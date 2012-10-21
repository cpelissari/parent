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
package br.com.objectos.comuns.relational.jdbc;

import static com.google.common.collect.Lists.transform;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;
import java.util.Set;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { RelationalJdbcTestModule.class })
public class NativeBatchInsertTest {

  @Inject
  private NativeBatchInsert batchInsert;

  @Inject
  private DBUnit dbUnit;
  @Inject
  private FindSimple findSimple;

  @BeforeClass
  public void prepararDBUnit() {
    dbUnit.loadDefaultDataSet();
  }

  public void batch_insert_of_insertables() {
    List<String> vals = ImmutableList.<String> builder()
        .add("123")
        .add("456")
        .build();

    List<Simple> bef = transform(vals, new LoadSimple());
    assertThat(bef.get(0), nullValue());
    assertThat(bef.get(1), nullValue());

    List<Simple> list = transform(vals, new ToSimple());
    batchInsert.insert(list);

    List<Simple> res = transform(vals, new LoadSimple());
    List<String> v = transform(res, new ToValue());
    assertThat(v, equalTo(vals));
  }

  public void batch_insert_page_test() {
    List<String> vals = ImmutableList.<String> builder()
        .add("1")
        .add("2")
        .add("3")
        .add("4")
        .add("5")
        .add("6")
        .add("7")
        .add("8")
        .add("9")
        .add("10")
        .build();

    List<Simple> bef = transform(vals, new LoadSimple());
    assertThat(bef.get(0), nullValue());
    assertThat(bef.get(1), nullValue());
    assertThat(bef.get(2), nullValue());
    assertThat(bef.get(3), nullValue());
    assertThat(bef.get(4), nullValue());
    assertThat(bef.get(5), nullValue());
    assertThat(bef.get(6), nullValue());
    assertThat(bef.get(7), nullValue());
    assertThat(bef.get(8), nullValue());
    assertThat(bef.get(9), nullValue());

    List<Simple> list = transform(vals, new ToSimple());
    batchInsert.insert(list);

    List<Simple> res = transform(vals, new LoadSimple());

    List<Integer> id = transform(res, new ToId());
    Set<Integer> idSet = ImmutableSet.copyOf(id);
    assertThat(idSet.size(), equalTo(vals.size()));
    assertThat(idSet.contains(null), is(false));

    List<String> v = transform(res, new ToValue());
    assertThat(v, equalTo(vals));
  }

  private class ToSimple implements Function<String, Simple> {
    @Override
    public Simple apply(String input) {
      return new Simple(input);
    }
  }

  private class LoadSimple implements Function<String, Simple> {
    @Override
    public Simple apply(String input) {
      return findSimple.byString(input);
    }
  }

  private class ToId implements Function<Simple, Integer> {
    @Override
    public Integer apply(Simple input) {
      return input.getId();
    }
  }

  private class ToValue implements Function<Simple, String> {
    @Override
    public String apply(Simple input) {
      return input.getString();
    }
  }

}