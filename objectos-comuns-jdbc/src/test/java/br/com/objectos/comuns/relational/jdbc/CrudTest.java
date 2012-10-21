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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { RelationalJdbcTestModule.class })
public class CrudTest {

  @Inject
  private Crud crud;

  @Inject
  private DBUnit dbUnit;
  @Inject
  private Provider<NativeSql> sqlProvider;

  @BeforeClass
  public void prepararDBUnit() {
    dbUnit.loadDefaultDataSet();
  }

  private final boolean booleanValue = true;
  private final int intValue = 123;
  private final long longValue = 345l;
  private final float floatValue = 5.67f;
  private final double doubleValue = 8.98;
  private final BigDecimal bigDecimal = new BigDecimal("7.89");
  private final Date javaDate = new Date();
  private final LocalDate localDate = new LocalDate(2010, 12, 25);
  private final DateTime dateTime = new DateTime();
  private final String text = "hello world!";

  public void insert() {
    final CrudEntity entity = new CrudEntity();

    crud.create(entity);

    CrudEntity pojo = findById(entity.id);
    assertThat(pojo.toString(), equalTo(entity.toString()));
  }

  public void update() {
    CrudEntity entity = findById(-1);
    String before = entity.toString();

    entity.booleanValue = booleanValue;
    entity.intValue = intValue;
    entity.longValue = longValue;
    entity.floatValue = floatValue;
    entity.doubleValue = doubleValue;
    entity.bigDecimal = bigDecimal;
    entity.javaDate = javaDate;
    entity.localDate = localDate;
    entity.dateTime = dateTime;
    entity.text = text;

    EntityMapping mapping = entity.toMapping();
    crud.update(mapping);

    assertThat(entity.toString(), not(equalTo(before)));
    entity = findById(-1);

    CrudEntity pojo = new CrudEntity().id(-1);
    assertThat(entity.toString(), equalTo(pojo.toString()));
  }

  public void delete() {
    CrudEntity entity = findById(-2);
    assertThat(entity, is(notNullValue()));

    crud.delete(entity);

    entity = findById(-2);
    assertThat(entity, is(nullValue()));
  }

  private CrudEntity findById(int id) {
    return newSelect()
        .add("where ID = ?").param(id)
        .single();
  }

  private NativeSql newSelect() {
    return sqlProvider.get()
        .add("select *")
        .add("from COMUNS_RELATIONAL.MAPPING")
        .andLoadWith(new CrudEntityLoader());
  }

}