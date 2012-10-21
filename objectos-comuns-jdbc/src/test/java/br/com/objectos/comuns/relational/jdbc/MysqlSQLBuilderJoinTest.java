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

import static com.google.common.collect.Lists.transform;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.relational.search.Join;
import br.com.objectos.comuns.relational.search.ResultSetLoader;
import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.common.base.Functions;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = RelationalJdbcTestModule.class)
public class MysqlSQLBuilderJoinTest {

  @Inject
  private DBUnit dbunit;

  @Inject
  private Provider<Sql> sqlProvider;

  private final ResultSetLoader<Other> loader = new ReflectionLoader<Other>(Other.class);

  @BeforeMethod
  public void reset() {
    dbunit.load(new MiniComunsJdbcTruncateXml());
    dbunit.load(new MiniComunsJdbcXml());
  }

  public void load_related_entity() {
    Sql sql = sqlProvider.get();
    sql.select("*").from("COMUNS_RELATIONAL.OTHER").as("O").andLoadWith(loader);
    sql.join("SIMPLE", "S").on("O.SIMPLE_ID = S.ID");
    sql.order("O.ID").ascending();

    List<Other> result = sql.list();
    List<String> strings = transform(result, Functions.toStringFunction());
    assertThat(strings.size(), equalTo(3));
    assertThat(strings.get(0), equalTo("Other{id=1, value=ABCBA}"));
    assertThat(strings.get(1), equalTo("Other{id=2, value=BCDCB}"));
    assertThat(strings.get(2), equalTo("Other{id=3, value=CDEDC}"));
  }

  public void restrict_on_related_entity() {
    Sql sql = sqlProvider.get();
    sql.select("*").from("COMUNS_RELATIONAL.OTHER").as("O").andLoadWith(loader);

    Join _simple = sql.join("SIMPLE", "S").on("O.SIMPLE_ID = S.ID");
    _simple.where("STRING").equalTo("BCD");

    List<Other> result = sql.list();
    List<String> strings = transform(result, Functions.toStringFunction());
    assertThat(strings.size(), equalTo(1));
    assertThat(strings.get(0), equalTo("Other{id=2, value=BCDCB}"));
  }

}