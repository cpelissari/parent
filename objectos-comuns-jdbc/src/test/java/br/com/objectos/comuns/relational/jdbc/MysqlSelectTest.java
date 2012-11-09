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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.common.base.Functions;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { RelationalJdbcTestModule.class })
public class MysqlSelectTest {

  @Inject
  private Provider<NativeSql> sqlProvider;

  @Inject
  private DBUnit dbUnit;

  @BeforeClass
  public void prepararDBUnit() {
    dbUnit.loadDefaultDataSet();
  }

  public void select_columns_should_return_records() {
    NativeSql sql = sqlProvider.get();

    List<Simple> result = sql
        .add("select * from COMUNS_RELATIONAL.SIMPLE as S")
        .add("order by S.ID asc")
        .andLoadWith(new SimpleEntityLoader())
        .list();

    assertThat(result.size(), equalTo(3));

    List<String> strings = transform(result, Functions.toStringFunction());
    assertThat(strings.get(0), equalTo("Simple{id=1, string=CDE}"));
    assertThat(strings.get(1), equalTo("Simple{id=2, string=BCD}"));
    assertThat(strings.get(2), equalTo("Simple{id=3, string=ABC}"));
  }

  public void select_with_explicit_column_names_should_be_ok() {
    NativeSql sql = sqlProvider.get();

    List<Simple> result = sql
        .add("select ID, STRING from COMUNS_RELATIONAL.SIMPLE as S")
        .add("order by ID asc")
        .andLoadWith(new SimpleEntityLoader())
        .list();

    assertThat(result.size(), equalTo(3));

    List<String> strings = transform(result, Functions.toStringFunction());
    assertThat(strings.get(0), equalTo("Simple{id=1, string=CDE}"));
    assertThat(strings.get(1), equalTo("Simple{id=2, string=BCD}"));
    assertThat(strings.get(2), equalTo("Simple{id=3, string=ABC}"));
  }

  public void sum_aggregate_should_be_ok() {
    NativeSql sql = sqlProvider.get();

    Long result = sql
        .add("select sum(ID) from COMUNS_RELATIONAL.SIMPLE as S")
        .andLoadWith(new LongLoader())
        .single();

    assertThat(result, equalTo(Long.valueOf(1 + 2 + 3)));
  }

}