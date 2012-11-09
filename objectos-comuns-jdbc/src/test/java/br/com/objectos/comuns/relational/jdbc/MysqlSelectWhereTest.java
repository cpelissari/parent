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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.relational.search.ResultSetLoader;
import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { RelationalJdbcTestModule.class })
public class MysqlSelectWhereTest {

  @Inject
  private Provider<NativeSql> sqlProvider;

  @Inject
  private DBUnit dbUnit;

  private NativeSql sql;
  private final ResultSetLoader<Simple> entityLoader = new SimpleEntityLoader();

  @BeforeMethod
  public void reset() {
    dbUnit.loadDefaultDataSet();

    sql = sqlProvider.get();

    sql.add("select * from COMUNS_RELATIONAL.SIMPLE as S")
        .andLoadWith(entityLoader);
  }

  public void equal_to() {
    sql.add("where STRING = ?")
        .add("order by S.ID asc")
        .param("CDE");

    List<Simple> result = sql.list();

    assertThat(result.size(), equalTo(1));
    assertThat(result.get(0).toString(), equalTo("Simple{id=1, string=CDE}"));
  }

  public void like() {
    sql.add("where STRING like concat('%',?,'%')")
        .add("order by S.ID asc")
        .param("D");

    List<Simple> result = sql.list();

    assertThat(result.size(), equalTo(2));
    assertThat(result.get(0).toString(), equalTo("Simple{id=1, string=CDE}"));
    assertThat(result.get(1).toString(), equalTo("Simple{id=2, string=BCD}"));
  }

  public void starts_with() {
    sql.add("where STRING like concat(?,'%')")
        .add("order by S.ID asc")
        .param("C");

    List<Simple> result = sql.list();

    assertThat(result.size(), equalTo(1));
    assertThat(result.get(0).toString(), equalTo("Simple{id=1, string=CDE}"));
  }

  public void ends_with() {
    sql.add("where STRING like concat('%', ?)")
        .add("order by S.ID asc")
        .param("E");

    List<Simple> result = sql.list();

    assertThat(result.size(), equalTo(1));
    assertThat(result.get(0).toString(), equalTo("Simple{id=1, string=CDE}"));
  }

  public void gt() {
    sql.add("where ID > ?")
        .add("order by S.ID asc")
        .param(2);

    List<Simple> result = sql.list();

    assertThat(result.size(), equalTo(1));
    assertThat(result.get(0).toString(), equalTo("Simple{id=3, string=ABC}"));
  }

  public void ge() {
    sql.add("where ID >= ?")
        .add("order by S.ID asc")
        .param(2);

    List<Simple> result = sql.list();

    assertThat(result.size(), equalTo(2));
    assertThat(result.get(0).toString(), equalTo("Simple{id=2, string=BCD}"));
    assertThat(result.get(1).toString(), equalTo("Simple{id=3, string=ABC}"));
  }

  public void lt() {
    sql.add("where ID < ?")
        .add("order by S.ID asc")
        .param(2);

    List<Simple> result = sql.list();

    assertThat(result.size(), equalTo(1));
    assertThat(result.get(0).toString(), equalTo("Simple{id=1, string=CDE}"));
  }

  public void le() {
    sql.add("where ID <= ?")
        .add("order by S.ID asc")
        .param(2);

    List<Simple> result = sql.list();

    assertThat(result.size(), equalTo(2));
    assertThat(result.get(0).toString(), equalTo("Simple{id=1, string=CDE}"));
    assertThat(result.get(1).toString(), equalTo("Simple{id=2, string=BCD}"));
  }

}