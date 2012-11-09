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
package br.com.objectos.comuns.sitebricks.relational;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.objectos.comuns.relational.search.Page;
import br.com.objectos.comuns.relational.search.ResultSetLoader;
import br.com.objectos.comuns.sitebricks.RequestWrapper;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class PagerLoader implements ResultSetLoader<Pager> {

  private final RequestWrapper wrapper;

  public PagerLoader(RequestWrapper wrapper) {
    this.wrapper = wrapper;
  }

  @Override
  public Pager load(ResultSet rs) throws SQLException {
    Page page = wrapper.getPage();

    int number = page.getNumber();
    int length = page.getLength();
    int total = rs.getInt(1);

    return new PagerJson(number, length, total);
  }

}