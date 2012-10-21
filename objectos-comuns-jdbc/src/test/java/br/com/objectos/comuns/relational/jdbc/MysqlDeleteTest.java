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
import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.relational.search.Join;
import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = RelationalJdbcTestModule.class)
public class MysqlDeleteTest {

  @Inject
  private Provider<DeleteDeprecated> deleteProvider;

  @Inject
  private DBUnit dbUnit;
  @Inject
  private FindDel findDel;

  @BeforeMethod
  public void setUpTestData() {
    dbUnit.loadDefaultDataSet();
  }

  public void delete_all_should_work_properly() {
    List<Del> before = findDel.all();
    assertThat(before.size(), equalTo(9));

    DeleteDeprecated op = deleteProvider.get();
    op.delete("DEL").from("COMUNS_RELATIONAL.DEL").as("DEL");

    int rows = op.execute();
    assertThat(rows, equalTo(9));

    List<Del> after = findDel.all();
    assertThat(after.size(), equalTo(0));
  }

  public void delete_where_should_work_properly() {
    List<Del> before = findDel.all();
    assertThat(before.size(), equalTo(9));

    DeleteDeprecated op = deleteProvider.get();
    op.delete("DEL") //
        .from("COMUNS_RELATIONAL.DEL") //
        .as("DEL");
    op.where("SIMPLE_ID").equalTo(2);

    int rows = op.execute();
    assertThat(rows, equalTo(3));

    List<Del> after = findDel.all();
    assertThat(after.size(), equalTo(6));

    List<Integer> ids = transform(after, new ToSimpleId());
    Set<Integer> uniq = newHashSet(ids);
    assertThat(uniq.contains(1), equalTo(true));
    assertThat(uniq.contains(3), equalTo(true));
  }

  public void delete_join_where_should_work_properly() {
    List<Del> before = findDel.all();
    assertThat(before.size(), equalTo(9));

    DeleteDeprecated op = deleteProvider.get();
    op.delete("DEL") //
        .from("COMUNS_RELATIONAL.DEL") //
        .as("DEL");

    Join _simple = op.join("COMUNS_RELATIONAL.SIMPLE", "SIMPLE") //
        .on("DEL.SIMPLE_ID = SIMPLE.ID");
    _simple.where("ID").equalTo(2);

    int rows = op.execute();
    assertThat(rows, equalTo(3));

    List<Del> after = findDel.all();
    assertThat(after.size(), equalTo(6));

    List<Integer> ids = transform(after, new ToSimpleId());
    Set<Integer> uniq = newHashSet(ids);
    assertThat(uniq.contains(1), equalTo(true));
    assertThat(uniq.contains(3), equalTo(true));
  }

  private class ToSimpleId implements Function<Del, Integer> {
    @Override
    public Integer apply(Del input) {
      return input.getSimpleId();
    }
  }

}