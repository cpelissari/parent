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
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.objectos.comuns.relational.search.Page;
import br.com.objectos.comuns.relational.search.ResultSetLoader;
import br.com.objectos.comuns.relational.search.SimplePage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class JdbcNativeSqlExecGuice implements JdbcNativeSqlExec {

  private static final Logger logger = LoggerFactory.getLogger(JdbcNativeSqlExecGuice.class);

  private final SQLProvider<Connection> connections;

  @Inject
  public JdbcNativeSqlExecGuice(SQLProvider<Connection> connections) {
    this.connections = connections;
  }

  @Override
  public int execute(NativeSqlImpl sql) {
    logger.debug("execute <<:sql={}", sql);

    int result = 0;

    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {

      connection = connections.get();
      stmt = sql.prepare(connection);
      result = stmt.executeUpdate();

    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    } finally {
      Jdbc.close(connection);
      Jdbc.close(stmt);
      Jdbc.close(rs);
    }

    return result;
  }

  @Override
  public <T> Iterator<T> iterate(NativeSqlImpl sql) {
    return new IteratorImpl<T>(this, sql);
  }

  @Override
  public void insert(NativeSqlImpl sql) {
    logger.debug("insert <<:sql=\n{}", sql);

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;

    try {

      connection = connections.get();
      statement = sql.prepare(connection, Statement.RETURN_GENERATED_KEYS);
      statement.executeUpdate();

      GeneratedKeyCallback keyCallback = sql.getKeyCallback();
      if (keyCallback != null) {
        rs = statement.getGeneratedKeys();
        keyCallback.set(rs);
        rs.close();
      }

    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    } finally {

      Jdbc.close(connection);
      Jdbc.close(statement);
      Jdbc.close(rs);

    }

  }

  @Override
  public <T> List<T> list(NativeSqlImpl sql) {
    logger.debug("list <<:sql=\n{}", sql);

    return list0(sql);
  }

  @Override
  public <T> List<T> listPage(NativeSqlImpl sql, Page page) {
    logger.debug("listPage <<:sql=\n{}", sql, page);

    sql.limit(page);
    return list0(sql);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T single(NativeSqlImpl sql) {
    logger.debug("single <<:sql=\n{}", sql);

    T result = null;

    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {

      connection = connections.get();
      stmt = sql.prepare(connection);
      ResultSetLoader<?> loader = sql.getLoader();
      rs = stmt.executeQuery();
      result = (T) (rs.next() ? loader.load(rs) : null);

    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    } finally {
      Jdbc.close(connection);
      Jdbc.close(stmt);
      Jdbc.close(rs);
    }

    return result;
  }

  private <T> List<T> list0(NativeSqlImpl sql) {
    List<T> result = null;

    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {

      connection = connections.get();
      stmt = sql.prepare(connection);
      ResultSetLoader<?> loader = sql.getLoader();
      rs = stmt.executeQuery();
      Iterator<ResultSet> iterator = new ResultSetIterator(rs);
      Iterator<T> loaded = Iterators.transform(iterator, new LoaderFunction<T>(loader));
      result = ImmutableList.copyOf(loaded);

    } catch (SQLException e) {
      String msg = sql != null ? sql.toString() : null;
      throw new SQLRuntimeException(msg, e);
    } finally {
      Jdbc.close(connection);
      Jdbc.close(stmt);
      Jdbc.close(rs);
    }

    return result;
  }

  private static class IteratorImpl<E> implements Iterator<E> {

    private final JdbcNativeSqlExec exec;

    private final NativeSqlImpl sql;

    private final int iterationLength = 50;

    private int page = 0;

    private Iterator<E> iterator;

    public IteratorImpl(JdbcNativeSqlExec exec, NativeSqlImpl sql) {
      this.exec = exec;
      this.sql = sql;
    }

    @Override
    public boolean hasNext() {
      if (iterator == null || !iterator.hasNext()) {
        updateIterator();
      }
      return iterator.hasNext();
    }

    @Override
    public E next() {
      return iterator.next();
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    private void updateIterator() {
      List<E> itens = getNextPage();
      iterator = itens.iterator();
    }

    private List<E> getNextPage() {
      int firstResult = firstResult();

      SimplePage paginator = SimplePage.build() //
          .startAt(firstResult) //
          .withLengthOf(iterationLength) //
          .get();

      page++;

      return exec.listPage(sql, paginator);
    }

    private int firstResult() {
      return page * iterationLength;
    }

  }

}