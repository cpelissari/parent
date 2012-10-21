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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.objectos.comuns.relational.jdbc.DeleteBuilder.DeleteAlias;
import br.com.objectos.comuns.relational.search.Element;
import br.com.objectos.comuns.relational.search.HasElements;
import br.com.objectos.comuns.relational.search.Join;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class DeleteConfigure extends JdbcConfigure {

  private static final Logger logger = LoggerFactory.getLogger(DeleteConfigure.class);

  public DeleteConfigure(HasElements sql) {
    super(sql);
  }

  @Override
  Logger getLogger() {
    return logger;
  }

  @Override
  void makeOperation(StringBuilder sql, List<Join> joins) {
    makeKey(sql, DeleteAlias.class);

    for (Element join : joins) {
      sql.append(join.toString());
    }
  }

}