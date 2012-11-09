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

import java.util.Iterator;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.relational.search.Page;
import br.com.objectos.comuns.relational.search.ResultSetLoader;
import br.com.objectos.comuns.relational.search.SimplePage;
import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { RelationalJdbcTestModule.class })
public class MysqlSelectPageTest {

  @Inject
  private Provider<NativeSql> sqlProvider;

  @Inject
  private DBUnit dbUnit;

  private NativeSql sql;
  private final ResultSetLoader<Many> loader = new ReflectionLoader<Many>(Many.class);

  @BeforeMethod
  public void reset() {
    dbUnit.loadDefaultDataSet();

    sql = sqlProvider.get()
        .add("select *")
        .add("from COMUNS_RELATIONAL.MANY")
        .add("order by ID asc")
        .andLoadWith(loader);
  }

  public void page_with_offset_zero() {
    int offset = 0;
    int pageLength = 10;

    Page page = new SimplePage.Builder() //
        .startAt(offset).withLengthOf(pageLength) //
        .get();

    List<Many> result = sql.listPage(page);
    assertThat(result.size(), equalTo(pageLength));
    assertThat(result.get(0).getValue(), equalTo(++offset));
    assertThat(result.get(1).getValue(), equalTo(++offset));
    assertThat(result.get(2).getValue(), equalTo(++offset));
    assertThat(result.get(3).getValue(), equalTo(++offset));
    assertThat(result.get(4).getValue(), equalTo(++offset));
    assertThat(result.get(5).getValue(), equalTo(++offset));
    assertThat(result.get(6).getValue(), equalTo(++offset));
    assertThat(result.get(7).getValue(), equalTo(++offset));
    assertThat(result.get(8).getValue(), equalTo(++offset));
    assertThat(result.get(9).getValue(), equalTo(++offset));
  }

  public void page_with_offset_gt_zero() {
    int offset = 10;
    int pageLength = 3;

    Page page = new SimplePage.Builder() //
        .startAt(offset).withLengthOf(pageLength) //
        .get();

    List<Many> result = sql.listPage(page);
    assertThat(result.size(), equalTo(pageLength));
    assertThat(result.get(0).getValue(), equalTo(++offset));
    assertThat(result.get(1).getValue(), equalTo(++offset));
    assertThat(result.get(2).getValue(), equalTo(++offset));
  }

  public void iterator_test() {
    int offset = 0;

    Iterator<Many> iter = sql.iterate();

    List<Many> result = ImmutableList.copyOf(iter);
    assertThat(result.size(), equalTo(20));
    assertThat(result.get(0).getValue(), equalTo(++offset));
    assertThat(result.get(1).getValue(), equalTo(++offset));
    assertThat(result.get(2).getValue(), equalTo(++offset));
    assertThat(result.get(3).getValue(), equalTo(++offset));
    assertThat(result.get(4).getValue(), equalTo(++offset));
    assertThat(result.get(5).getValue(), equalTo(++offset));
    assertThat(result.get(6).getValue(), equalTo(++offset));
    assertThat(result.get(7).getValue(), equalTo(++offset));
    assertThat(result.get(8).getValue(), equalTo(++offset));
    assertThat(result.get(9).getValue(), equalTo(++offset));
  }

}