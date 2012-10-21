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

import com.google.common.base.Function;
import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { RelationalJdbcTestModule.class })
public class EagerFetchTest {

  @Inject
  private EagerQuery query;

  @Inject
  private DBUnit dbUnit;

  @BeforeClass
  public void prepararDBUnit() {
    dbUnit.loadDefaultDataSet();
  }

  public void eager_fetch() {
    List<Eager> res = query.list();

    assertThat(res.size(), equalTo(3));

    List<String> val = transform(res, new ToEagerValue());
    assertThat(val.get(0), equalTo("A"));
    assertThat(val.get(1), equalTo("B"));
    assertThat(val.get(2), equalTo("C"));

    List<List<EagerMany>> many = transform(res, new ToManyList());
    assertThat(many.get(0).size(), equalTo(2));
    assertThat(many.get(1).size(), equalTo(0));
    assertThat(many.get(2).size(), equalTo(1));

    List<EagerMany> many0 = many.get(0);
    assertThat(many0.get(0).getMany(), equalTo("A1"));
    assertThat(many0.get(1).getMany(), equalTo("A2"));

    List<EagerMany> many2 = many.get(2);
    assertThat(many2.get(0).getMany(), equalTo("C1"));
  }

  private class ToEagerValue implements Function<Eager, String> {
    @Override
    public String apply(Eager input) {
      return input.getValue();
    }
  }

  private class ToManyList implements Function<Eager, List<EagerMany>> {
    @Override
    public List<EagerMany> apply(Eager input) {
      return input.getMany();
    }
  }

}