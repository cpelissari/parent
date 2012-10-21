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

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.google.common.base.Objects;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class CrudEntity implements EntityJdbc {

  int id;

  boolean booleanValue = true;

  int intValue = 123;

  long longValue = 345l;

  float floatValue = 5.67f;

  double doubleValue = 8.98;

  BigDecimal bigDecimal = new BigDecimal("7.89");

  Date javaDate = new Date();

  LocalDate localDate = new LocalDate(2010, 12, 25);

  DateTime dateTime = new DateTime();

  String text = "hello world!";

  public CrudEntity() {
  }

  public CrudEntity(ResultSetWrapper rs) {
    this.id = rs.getInt("MAPPING.ID");
    this.booleanValue = rs.getBoolean("BOOLEAN_VALUE");
    this.intValue = rs.getInt("INT_VALUE");
    this.longValue = rs.getLong("LONG_VALUE");
    this.floatValue = rs.getFloat("FLOAT_VALUE");
    this.doubleValue = rs.getDouble("DOUBLE_VALUE");
    this.bigDecimal = rs.getBigDecimal("BIG_DECIMAL");
    this.javaDate = rs.getDate("JAVA_DATE");
    this.localDate = rs.getLocalDate("LOCAL_DATE");
    this.dateTime = rs.getDateTime("DATE_TIME");
    this.text = rs.getString("TEXT");
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .addValue(id)
        .addValue(booleanValue)
        .addValue(intValue)
        .addValue(floatValue)
        .addValue(doubleValue)
        .addValue(bigDecimal)
        .addValue(new DateTime(javaDate).withTimeAtStartOfDay())
        .addValue(localDate)
        .addValue(dateTime.withTimeAtStartOfDay())
        .addValue(text)
        .toString();
  }

  @Override
  public EntityMapping toMapping() {
    return Relational.table("COMUNS_RELATIONAL.MAPPING")

        .id("ID", id, new GeneratedKeyListener() {
          @Override
          public void set(GeneratedKey key) {
            id = key.getInt();
          }
        })

        .col("BOOLEAN_VALUE", booleanValue)
        .col("INT_VALUE", intValue)
        .col("LONG_VALUE", longValue)
        .col("FLOAT_VALUE", floatValue)
        .col("DOUBLE_VALUE", doubleValue)
        .col("BIG_DECIMAL", bigDecimal)
        .col("JAVA_DATE", javaDate)
        .col("LOCAL_DATE", localDate)
        .col("DATE_TIME", dateTime)
        .col("TEXT", text);
  }

  public CrudEntity id(int id) {
    this.id = id;
    return this;
  }

  public CrudEntity booleanValue(boolean booleanValue) {
    this.booleanValue = booleanValue;
    return this;
  }

  public CrudEntity intValue(int intValue) {
    this.intValue = intValue;
    return this;
  }

  public CrudEntity longValue(long longValue) {
    this.longValue = longValue;
    return this;
  }

  public CrudEntity floatValue(float floatValue) {
    this.floatValue = floatValue;
    return this;
  }

  public CrudEntity doubleValue(double doubleValue) {
    this.doubleValue = doubleValue;
    return this;
  }

  public CrudEntity bigDecimal(BigDecimal bigDecimal) {
    this.bigDecimal = bigDecimal;
    return this;
  }

  public CrudEntity date(Date javaDate) {
    this.javaDate = javaDate;
    return this;
  }

  public CrudEntity localDate(LocalDate localDate) {
    this.localDate = localDate;
    return this;
  }

  public CrudEntity dateTime(DateTime dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  public CrudEntity text(String text) {
    this.text = text;
    return this;
  }

}