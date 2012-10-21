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

import org.testng.annotations.BeforeClass;
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
@Guice(modules = RelationalJdbcTestModule.class)
public class AtomicMergeTest {

  private final ResultSetLoader<Duo> loader = new ReflectionLoader<Duo>(Duo.class);

  @Inject
  private AtomicMerge atomicMerge;

  @Inject
  private DBUnit dbunit;
  @Inject
  private Provider<Sql> sqlProvider;

  @BeforeClass
  public void prepareDBUnit() {
    dbunit.loadDefaultDataSet();
  }

  public void update_all_should_work_properly() {
    List<Duo> before = findAll();

    assertThat(before.size(), equalTo(6));
    assertThat(before.get(0).toString(), equalTo("Duo{id=1, a=A, b=A}"));
    assertThat(before.get(1).toString(), equalTo("Duo{id=2, a=A, b=B}"));
    assertThat(before.get(2).toString(), equalTo("Duo{id=3, a=A, b=C}"));
    assertThat(before.get(3).toString(), equalTo("Duo{id=4, a=B, b=A}"));
    assertThat(before.get(4).toString(), equalTo("Duo{id=5, a=B, b=B}"));
    assertThat(before.get(5).toString(), equalTo("Duo{id=6, a=B, b=C}"));

    Duo duo = new Duo("XXX", "ZZZ");
    atomicMerge.update(duo).with(new PrimaryKey(3));

    List<Duo> after = findAll();

    assertThat(after.size(), equalTo(6));
    assertThat(after.get(0).toString(), equalTo("Duo{id=1, a=A, b=A}"));
    assertThat(after.get(1).toString(), equalTo("Duo{id=2, a=A, b=B}"));
    assertThat(after.get(2).toString(), equalTo("Duo{id=3, a=XXX, b=ZZZ}"));
    assertThat(after.get(3).toString(), equalTo("Duo{id=4, a=B, b=A}"));
    assertThat(after.get(4).toString(), equalTo("Duo{id=5, a=B, b=B}"));
    assertThat(after.get(5).toString(), equalTo("Duo{id=6, a=B, b=C}"));
  }

  private List<Duo> findAll() {
    Sql sql = sqlProvider.get();
    sql.select("*").from("COMUNS_RELATIONAL.DUO").as("D").andLoadWith(loader);
    sql.order("ID").ascending();
    return sql.list();
  }

}