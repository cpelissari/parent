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

import br.com.objectos.comuns.relational.search.Configuracao;
import br.com.objectos.comuns.relational.search.ConsultaDinamica;
import br.com.objectos.comuns.relational.search.Page;
import br.com.objectos.comuns.relational.search.SQLBuilder;
import br.com.objectos.comuns.relational.search.SearchQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class JdbcSearchQueryExec implements br.com.objectos.comuns.relational.jdbc.SearchQueryExec {

  private final JdbcSQLBuilderExec exec;

  private final Provider<SQLBuilder> sqlProvider;

  @Inject
  public JdbcSearchQueryExec(JdbcSQLBuilderExec exec, Provider<SQLBuilder> sqlProvider) {
    this.exec = exec;
    this.sqlProvider = sqlProvider;
  }

  @Override
  public <E> List<E> list(SearchQuery<E> query) {
    SQLBuilder sql = sqlProvider.get();
    query.configure(sql);
    return exec.list(sql);
  }

  @Override
  public <E> List<E> listPage(SearchQuery<E> query, Page page) {
    SQLBuilder sql = sqlProvider.get();
    query.configure(sql);
    return exec.listPage(sql, page);
  }

  @Override
  public int count(SearchQuery<?> query) {
    return 0;
  }

  @Override
  public int contarTotal(Configuracao configuracao) {
    return 0;
  }

  @Override
  public <E> ConsultaDinamica<E> executar(Configuracao configuracao) {
    return null;
  }

  @Override
  public <E> List<E> obterRegistros(Configuracao configuracao) {
    return null;
  }

}