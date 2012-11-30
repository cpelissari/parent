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
package br.com.objectos.comuns.testing.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.objectos.comuns.relational.jdbc.SQLProvider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
abstract class AbstractTruncate implements Truncate {

  private final SQLProvider<Connection> connections;

  public AbstractTruncate(SQLProvider<Connection> connections) {
    this.connections = connections;
  }

  @Override
  public final void table(String name) {
    Connection connection = null;

    try {

      connection = connections.get();
      connection.setAutoCommit(false);

      disableReferentialIntegrity(connection);

      truncateTable(connection, name);

      enableReferentialIntegrity(connection);

      connection.commit();

    } catch (SQLException e) {
      throw new TestingRuntimeException(e);
    } finally {
      Jdbc.close(connection);
    }
  }

  private void disableReferentialIntegrity(Connection connection) throws SQLException {
    PreparedStatement stmt = null;

    try {
      String sql = getDisableReferentialIntegritySql();
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    } finally {
      Jdbc.close(stmt);
    }
  }

  private void truncateTable(Connection connection, String name) throws SQLException {
    PreparedStatement stmt = null;

    try {
      String sql = getTruncateTableSql(name);
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    } finally {
      Jdbc.close(stmt);
    }
  }

  private void enableReferentialIntegrity(Connection connection) throws SQLException {
    PreparedStatement stmt = null;

    try {
      String sql = getEnableReferentialIntegritySql();
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    } finally {
      Jdbc.close(stmt);
    }
  }

  abstract String getDisableReferentialIntegritySql();

  abstract String getTruncateTableSql(String name);

  abstract String getEnableReferentialIntegritySql();

}