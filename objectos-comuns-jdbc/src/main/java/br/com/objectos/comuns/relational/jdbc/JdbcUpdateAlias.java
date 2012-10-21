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

import br.com.objectos.comuns.relational.jdbc.UpdateBuilder.UpdateAlias;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class JdbcUpdateAlias extends AbstractJdbcElement implements UpdateAlias {

  private final String table;
  private String alias;

  public JdbcUpdateAlias(String table) {
    this.table = table;
  }

  @Override
  public void as(String alias) {
    this.alias = alias;
  }

  @Override
  protected void validateState() {
    Preconditions.checkNotNull(table, "A table to select from MUST be defined.");
    Preconditions.checkNotNull(alias, "You MUST define an alias for the table.");
  }

  @Override
  public String toString() {
    validateState();

    return String.format("update %s as %s%s", table, alias, New.Line);
  }

}