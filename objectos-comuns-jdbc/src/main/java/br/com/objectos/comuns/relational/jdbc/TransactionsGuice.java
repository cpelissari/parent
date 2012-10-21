/*
 * Copyright 2011 Objectos, Fábrica de Software LTDA.
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class TransactionsGuice implements Transactions {

  private final SQLProvider<Connection> connections;

  @Inject
  public TransactionsGuice(SQLProvider<Connection> connections) {
    this.connections = connections;
  }

  @Override
  public void execute(AtomicInsertOperation operation) throws TransactionRolledbackException {

    Connection conn = null;

    try {

      conn = connections.get();
      conn.setAutoCommit(false);
      Savepoint savepoint = conn.setSavepoint();

      Insertion insertion = new InsertionImpl(conn);

      try {

        operation.execute(insertion);

      } catch (SQLException inner) {

        conn.rollback(savepoint);
        throw new TransactionRolledbackException(inner);

      }

      conn.commit();

    } catch (SQLException e) {

      e.printStackTrace();

    } finally {

      Jdbc.close(conn);

    }

  }

  @Override
  public <I extends Insertable> I executeUpdate(AtomicUpdateOperation<I> operation)
      throws TransactionRolledbackException {

    I entity = null;

    Connection conn = null;

    try {

      conn = connections.get();
      conn.setAutoCommit(false);
      Savepoint savepoint = conn.setSavepoint();

      UpdateImpl update = new UpdateImpl(conn);

      try {

        entity = operation.execute(update);

      } catch (SQLException inner) {

        conn.rollback(savepoint);
        throw new TransactionRolledbackException(inner);

      }

      conn.commit();

    } catch (SQLException e) {

      e.printStackTrace();

    } finally {

      Jdbc.close(conn);

    }

    return entity;

  }

  private class InsertionImpl implements Insertion {

    private final Connection conn;

    public InsertionImpl(Connection conn) {
      this.conn = conn;
    }

    @Override
    public void of(Insertable entity) throws SQLException {

      Insert insert = entity.getInsert();
      of(insert);

    }

    @Override
    public void of(Insert insert) throws SQLException {

      PreparedStatement statement = null;
      ResultSet rs = null;

      try {

        String sql = insert.toString();
        statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        Stmt stmt = new PreparedStatementWrapper(statement);
        insert.prepare(stmt);

        statement.executeUpdate();

        GeneratedKeyCallback keyCallback = insert.getKeyCallback();
        if (keyCallback != null) {
          rs = statement.getGeneratedKeys();
          keyCallback.set(rs);
          rs.close();
        }

      } finally {

        Jdbc.close(statement);
        Jdbc.close(rs);

      }

    }

  }

  private class UpdateImpl implements Update {

    private final Connection conn;

    public UpdateImpl(Connection conn) {
      this.conn = conn;
    }

    @Override
    public void of(PrimaryKey key, Insertable entity) throws SQLException {

      PreparedStatement statement = null;
      ResultSet rs = null;

      try {

        Insert insert = entity.getInsert();
        String sql = insert.toUpdate(key);
        statement = conn.prepareStatement(sql);
        Stmt stmt = new PreparedStatementWrapper(statement);
        insert.prepare(stmt);

        statement.executeUpdate();

      } finally {

        Jdbc.close(statement);
        Jdbc.close(rs);

      }

    }

  }

}