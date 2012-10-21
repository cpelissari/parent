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

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.objectos.comuns.relational.jdbc.UpdateBuilder.UpdateAlias;
import br.com.objectos.comuns.relational.search.Element;
import br.com.objectos.comuns.relational.search.HasElements;
import br.com.objectos.comuns.relational.search.Join;

import com.google.common.base.Joiner;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class UpdateConfigure extends JdbcConfigure {

  private static final Logger logger = LoggerFactory.getLogger(UpdateConfigure.class);

  public UpdateConfigure(HasElements sql) {
    super(sql);
  }

  @Override
  void configureWhere(CountingStatement countingStatement, List<Element> whereEls) {

    Collection<Element> setters = getElements(UpdateSet.class);
    for (Element setter : setters) {
      setter.configure(countingStatement);
    }

    super.configureWhere(countingStatement, whereEls);

  }

  @Override
  Logger getLogger() {
    return logger;
  }

  @Override
  void makeOperation(StringBuilder sql, List<Join> joins) {
    makeKey(sql, UpdateAlias.class);

    for (Element join : joins) {
      sql.append(join.toString());
    }

    Collection<Element> setters = getElements(UpdateSet.class);
    String setter = Joiner.on("," + New.Line).join(setters);
    sql.append("set " + setter + New.Line);
  }

}