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
package br.com.objectos.comuns.testing.dbunit;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class MySqlMetadataHandler extends org.dbunit.ext.mysql.MySqlMetadataHandler {

  private final Set<String> catalogs;

  public MySqlMetadataHandler(Set<String> catalogs) {
    this.catalogs = catalogs;
  }

  @Override
  public ResultSet getTables(DatabaseMetaData metaData, String schemaName, String[] tableType)
      throws SQLException {

    if (catalogs.isEmpty()) {

      return super.getTables(metaData, schemaName, tableType);

    } else {

      Iterable<List<MysqlTable>> listses = Iterables.transform(catalogs, new ToTableResultSet(
          metaData, tableType));

      Iterable<MysqlTable> tables = Iterables.concat(listses);

      return new ResultSetImpl(tables);

    }

  }

  private static class ResultSetImpl extends EmptyResultSet {
    private final Iterator<MysqlTable> tables;

    private MysqlTable current;

    public ResultSetImpl(Iterable<MysqlTable> tables) {
      List<MysqlTable> _tables = ImmutableList.copyOf(tables);
      this.tables = _tables.iterator();
    }

    @Override
    public void close() throws SQLException {
    }

    @Override
    public boolean next() throws SQLException {
      boolean hasNext = tables.hasNext();

      if (hasNext) {
        current = tables.next();
      }

      return hasNext;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
      switch (columnIndex) {
      case 1:
        return current.catalogName;
      case 2:
        return current.schemaName;
      case 3:
        return current.tableName;
      default:
        return null;
      }
    }
  }

  private static class ToTableResultSet implements Function<String, List<MysqlTable>> {
    private final DatabaseMetaData metaData;
    private final String[] tableType;

    public ToTableResultSet(DatabaseMetaData metaData, String[] tableType) {
      this.metaData = metaData;
      this.tableType = tableType;
    }

    @Override
    public List<MysqlTable> apply(String input) {
      ResultSet rs = null;

      try {

        rs = metaData.getTables(input, null, "%", tableType);
        ResultSetIterator iter = new ResultSetIterator(rs);
        Iterator<MysqlTable> tables = Iterators.transform(iter, new ToTable());
        return ImmutableList.copyOf(tables);

      } catch (SQLException e) {
        throw Throwables.propagate(e);
      } finally {

        if (rs != null) {
          try {
            rs.close();
          } catch (SQLException e) {
          }
        }

      }

    }
  }

  private static class ToTable implements Function<ResultSet, MysqlTable> {
    @Override
    public MysqlTable apply(ResultSet rs) {
      try {
        MysqlTable table = new MysqlTable(rs);
        MysqlConfig.logger.debug("Adding {}", table);
        return table;
      } catch (SQLException e) {
        throw Throwables.propagate(e);
      }
    }
  }

}