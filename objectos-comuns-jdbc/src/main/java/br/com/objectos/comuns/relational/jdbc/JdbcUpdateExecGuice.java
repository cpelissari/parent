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

import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class JdbcUpdateExecGuice implements JdbcUpdateExec {

  private final SQLProvider<Connection> connections;

  @Inject
  public JdbcUpdateExecGuice(SQLProvider<Connection> connections) {
    this.connections = connections;
  }

  @Override
  public int execute(UpdateBuilder update) {
    int result = 0;

    Connection connection = null;
    JdbcConfigure configure = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {

      connection = connections.get();
      configure = new UpdateConfigure(update);
      stmt = configure.prepare(connection);
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

}