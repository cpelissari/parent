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
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.objectos.comuns.relational.search.Element;
import br.com.objectos.comuns.relational.search.HasElements;
import br.com.objectos.comuns.relational.search.Join;
import br.com.objectos.comuns.relational.search.ResultSetLoader;
import br.com.objectos.comuns.relational.search.SelectColumns;

import com.google.common.collect.Iterators;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class SelectConfigure extends JdbcConfigure {

  private static final Logger logger = LoggerFactory.getLogger(SelectConfigure.class);

  private ResultSetLoader<?> loader;

  public SelectConfigure(HasElements sql) {
    super(sql);
  }

  @Override
  public PreparedStatement prepare(Connection connection) {
    try {

      PreparedStatement stmt = tryToMake(connection);
      loader = extractLoader();
      return stmt;

    } catch (SQLException e) {

      throw new SQLRuntimeException(e);

    }
  }

  public ResultSetLoader<?> getLoader() {
    return loader;
  }

  @Override
  Logger getLogger() {
    return logger;
  }

  @Override
  void makeOperation(StringBuilder sql, List<Join> joins) {
    makeKey(sql, SelectColumns.class);

    for (Element join : joins) {
      sql.append(join.toString());
    }
  }

  private ResultSetLoader<?> extractLoader() {
    Collection<Element> els = getElements(ResultSetLoaderElement.class);
    Element el = Iterators.getOnlyElement(els.iterator());
    ResultSetLoaderElement loaderEl = (ResultSetLoaderElement) el;
    return loaderEl.getLoader();
  }

}