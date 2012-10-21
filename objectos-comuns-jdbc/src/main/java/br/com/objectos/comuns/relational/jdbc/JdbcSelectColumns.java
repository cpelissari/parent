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

import java.util.List;

import br.com.objectos.comuns.relational.search.ResultSetLoader;
import br.com.objectos.comuns.relational.search.SelectColumns;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class JdbcSelectColumns extends AbstractJdbcElement implements SelectColumns {

  private final AbstractJdbcSQLFunction builderListener;

  private final List<String> columns;

  private String table;
  private String alias;
  private ResultSetLoader<?> loader;

  public JdbcSelectColumns(AbstractJdbcSQLFunction builderListener, String... columns) {
    this.builderListener = builderListener;
    this.columns = ImmutableList.copyOf(columns);
  }

  @Override
  public SelectAs from(String table) {
    this.table = table;
    return new SelectAsImpl();
  }

  @Override
  public String getAlias() {
    return alias;
  }

  @Override
  protected void validateState() {
    Preconditions.checkNotNull(table, "A table to select from MUST be defined.");
    Preconditions.checkNotNull(alias, "You MUST define an alias for the table.");
    Preconditions.checkNotNull(loader, "You MUST define a ResultSetLoader for this query.");
  }

  @Override
  public String toString() {
    validateState();

    String cols = Joiner.on(", ").skipNulls().join(columns);
    return String.format("select %s from %s as %s%s", cols, table, alias, New.Line);
  }

  private class SelectAsImpl implements SelectAs {
    @Override
    public SelectLoader as(String alias) {
      Preconditions.checkNotNull(alias, "alias");

      JdbcSelectColumns.this.alias = alias;
      builderListener.setTableAlias(alias);

      return new SelectLoaderImpl();
    }
  }

  private class SelectLoaderImpl implements SelectLoader {
    @Override
    public void andLoadWith(ResultSetLoader<?> loader) {
      Preconditions.checkNotNull(loader, "loader");

      JdbcSelectColumns.this.loader = loader;
      builderListener.putElement(ResultSetLoaderElement.class, new ResultSetLoaderElement(loader));
    }
  }

}