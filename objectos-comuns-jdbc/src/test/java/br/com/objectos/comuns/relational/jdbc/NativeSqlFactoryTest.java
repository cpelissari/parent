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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.testng.Assert.assertFalse;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.relational.RelationalException;
import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { RelationalJdbcTestModule.class })
public class NativeSqlFactoryTest {

  @Inject
  private NativeSqlFactory sqlFactory;

  @Inject
  private DBUnit dbUnit;
  @Inject
  private FindSimple findSimple;

  @BeforeClass
  public void prepararDBUnit() {
    dbUnit.loadDefaultDataSet();
  }

  public void cascaded_insert_shoud_work_as_expected() {
    final String val0 = "123";
    final String val1 = "456";

    Simple simple0 = findSimple.byString(val0);
    assertThat(simple0, nullValue());

    Simple simple1 = findSimple.byString(val1);
    assertThat(simple1, nullValue());

    ListInsertable entity = new ListInsertable() {
      @Override
      public List<Insert> getInserts() {
        return ImmutableList.<Insert> builder()

            .add(new Simple(val0).getInsert())
            .add(new Simple(val1).getInsert())

            .build();
      }
    };

    sqlFactory.insertMany(entity).insert();

    simple0 = findSimple.byString(val0);
    assertThat(simple0, notNullValue());

    simple1 = findSimple.byString(val1);
    assertThat(simple1, notNullValue());
  }

  public void when_last_fails_first_should_be_rolledbacked() {
    final String val0 = "XXX";
    final String val1 = "ABC"; // fails unique constraint

    Simple simple0 = findSimple.byString(val0);
    assertThat(simple0, nullValue());

    Simple simple1 = findSimple.byString(val1);
    assertThat(simple1, notNullValue());

    ListInsertable entity = new ListInsertable() {
      @Override
      public List<Insert> getInserts() {
        return ImmutableList.<Insert> builder()

            .add(new Simple(val0).getInsert())
            .add(new Simple(val1).getInsert())

            .build();
      }
    };

    try {
      sqlFactory.insertMany(entity).insert();
      assertFalse(true);
    } catch (RelationalException e) {

    }

    simple0 = findSimple.byString(val0);
    assertThat(simple0, nullValue());

    simple1 = findSimple.byString(val1);
    assertThat(simple1, notNullValue());
  }

}