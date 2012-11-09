/*
 * Copyright 2012 Objectos, Fábrica de Software LTDA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package br.com.objectos.comuns.relational.jdbc;

import static com.google.common.collect.Lists.newArrayListWithCapacity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class NativeBatchInsertGuice implements NativeBatchInsert {

  private final BatchSize batchSize;

  private final SQLProvider<Connection> connections;

  private final ListInsertableExec listInsertableExec;

  @Inject
  public NativeBatchInsertGuice(BatchSize batchSize,
                                SQLProvider<Connection> connections,
                                ListInsertableExec listInsertableExec) {
    this.batchSize = batchSize;
    this.connections = connections;
    this.listInsertableExec = listInsertableExec;
  }

  @Override
  public <I extends Insertable> void insert(Iterator<I> entities) {
    PeekingIterator<I> iterator = Iterators.peekingIterator(entities);

    if (!iterator.hasNext()) {
      return;
    }

    I first = iterator.peek();
    Insert firstInsert = first.getInsert();
    String sql = firstInsert.toString();
    insert0(sql, iterator);
  }

  @Override
  public <I extends Insertable> void insert(Iterable<I> entities) {
    insert(entities.iterator());
  }

  @Override
  public void insertMany(Iterator<? extends ListInsertable> entities) {
    while (entities.hasNext()) {
      ListInsertable entity = entities.next();
      listInsertableExec.execute(entity);
    }
  }

  @Override
  public void insertMany(Iterable<? extends ListInsertable> entities) {
    insertMany(entities.iterator());
  }

  private <I extends Insertable> void insert0(String sql, Iterator<I> entities) {
    final int flushSize = batchSize.get();
    int count = 0;
    List<CallbackWrapper> callbacks = newArrayListWithCapacity(flushSize);
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = connections.get();
      connection.setAutoCommit(false);
      statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      PreparedStatementWrapper wrapper = new PreparedStatementWrapper(statement);

      while (entities.hasNext()) {
        I entity = entities.next();

        Insert insert = entity.getInsert();
        insert.prepare(wrapper);
        statement.addBatch();

        GeneratedKeyCallback keyCallback = insert.getKeyCallback();
        callbacks.add(new CallbackWrapper(keyCallback));

        if (++count % flushSize == 0) {
          new Flusher(statement, callbacks).flush();
        }
      }

      new Flusher(statement, callbacks).flush();

      connection.commit();
    } catch (SQLException e) {
      Jdbc.rollback(connection);
      throw new SQLRuntimeException(e);
    } finally {
      Jdbc.close(statement);
      Jdbc.close(connection);
    }
  }

  private class CallbackWrapper {

    private final GeneratedKeyCallback keyCallback;

    public CallbackWrapper(GeneratedKeyCallback keyCallback) {
      this.keyCallback = keyCallback;
    }

    public void set(ResultSet rs) throws SQLException {
      if (keyCallback != null) {
        keyCallback.set(rs);
      }
    }

  }

  private class Flusher {

    private final PreparedStatement statement;
    private final List<CallbackWrapper> callbacks;

    public Flusher(PreparedStatement statement, List<CallbackWrapper> callbacks) {
      this.statement = statement;
      this.callbacks = callbacks;
    }

    public void flush() throws SQLException {
      ResultSet rs = null;

      statement.executeBatch();

      try {
        rs = statement.getGeneratedKeys();

        for (CallbackWrapper callback : callbacks) {
          callback.set(rs);
        }
      } finally {
        callbacks.clear();
        Jdbc.close(rs);
      }
    }

  }

}