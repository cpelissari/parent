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
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.relational.UniqueInsert;
import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.common.base.Supplier;
import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { RelationalJdbcTestModule.class })
public class UniqueInsertTest {

  @Inject
  private DBUnit dbunit;

  @Inject
  private FindSimple findSimple;

  @Inject
  private UniqueInsert uniqueInsert;

  @BeforeClass
  public void prepare() {
    dbunit.load(new MiniComunsJdbcTruncateXml());
  }

  @Test(invocationCount = 3)
  public void insert_should_return_existing_if_key_is_violated() {
    final String value = "ZZZ";

    Simple entity = new Simple(value);
    Simple result = uniqueInsert.of(entity, new Supplier<Simple>() {
      @Override
      public Simple get() {
        return findSimple.byString(value);
      }
    });

    assertThat(result, notNullValue());
    assertThat(result.getString(), equalTo(value));
  }

  @Test(expectedExceptions = { UniqueInsertOperationException.class }, //
  expectedExceptionsMessageRegExp = "Tried to return found entity but got null")
  public void insert_should_not_work_if_finder_returns_null() {
    final String value = "ZZ1";

    Simple entity = new Simple(value);
    Simple result = uniqueInsert.of(entity, new Supplier<Simple>() {
      @Override
      public Simple get() {
        return null;
      }
    });
    assertThat(result, notNullValue());

    uniqueInsert.of(entity, new Supplier<Simple>() {
      @Override
      public Simple get() {
        return null;
      }
    });
  }

}