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

import br.com.objectos.comuns.relational.jdbc.DeleteBuilder.DeleteAlias;
import br.com.objectos.comuns.relational.jdbc.DeleteBuilder.DeleteFrom;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class JdbcDeleteAlias extends AbstractJdbcElement implements DeleteAlias {

  private final List<String> aliasesToDelete;

  private String table;
  private String alias;

  public JdbcDeleteAlias(AnsiDeleteBuilder ansiDeleteBuilder, String... aliases) {
    this.aliasesToDelete = ImmutableList.copyOf(aliases);
  }

  @Override
  public DeleteFrom from(String table) {
    this.table = table;
    return new DeleteFromImpl();
  }

  @Override
  protected void validateState() {
    Preconditions.checkNotNull(table, "A table to select from MUST be defined.");
    Preconditions.checkNotNull(alias, "You MUST define an alias for the table.");
  }

  @Override
  public String toString() {
    validateState();

    String cols = Joiner.on(", ").skipNulls().join(aliasesToDelete);
    return String.format("delete %s from %s as %s%s", cols, table, alias, New.Line);
  }

  private class DeleteFromImpl implements DeleteFrom {
    @Override
    public void as(String alias) {
      JdbcDeleteAlias.this.alias = alias;
    }
  }

}