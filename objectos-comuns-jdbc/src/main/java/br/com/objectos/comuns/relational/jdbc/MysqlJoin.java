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

import javax.persistence.criteria.JoinType;

import br.com.objectos.comuns.relational.search.Join;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class MysqlJoin extends MysqlSQLBuilder implements Join {

  private final JoinType joinType;
  private final String table;

  private String condition;

  public MysqlJoin(JoinType joinType, String table, String alias) {
    this.joinType = joinType;
    this.table = table;
    this.tableAlias = alias;
  }

  @Override
  public Object configure(Object source) {
    return null;
  }

  @Override
  public String getAlias() {
    return tableAlias;
  }

  @Override
  public Join on(String condition) {
    this.condition = condition;
    return this;
  }

  @Override
  public String toString() {
    String prefix = "";

    switch (joinType) {
    case INNER:
      prefix = "inner";

      break;
    case LEFT:
      prefix = "left outer";

      break;
    case RIGHT:
      prefix = "right outer";

      break;
    }

    return String
        .format("%s join %s as %s on %s%s", prefix, table, tableAlias, condition, New.Line);
  }

}