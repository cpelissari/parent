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

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class FindDelGuice implements FindDel {

  private final ResultSetLoader<Del> loader = new ReflectionLoader<Del>(Del.class);

  private final Provider<Sql> sqlProvider;

  @Inject
  public FindDelGuice(Provider<Sql> sqlProvider) {
    this.sqlProvider = sqlProvider;
  }

  @Override
  public List<Del> all() {
    Sql sql = sqlProvider.get();

    sql.select("*").from("DEL").as("DEL").andLoadWith(loader);
    sql.join("SIMPLE", "S").on("DEL.SIMPLE_ID = S.ID");

    return sql.list();
  }

}