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

import static com.google.common.collect.Lists.newArrayList;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Inserts implements HasInsertMethods<Inserts> {

  private final List<Insert> inserts = newArrayList();

  private Insert current;

  public Inserts(Insert current) {
    setCurrent(current);
  }

  private Inserts(List<Insert> inserts) {
    this.inserts.addAll(inserts);
    this.current = null;
  }

  private void setCurrent(Insert current) {
    this.current = current;
    inserts.add(current);
  }

  public static Inserts firstInto(String table) {
    return new Inserts(Insert.into(table));
  }

  public static Inserts firstThisInstance(Insertable insertable) {
    Insert current = insertable.getInsert();
    return new Inserts(current);
  }

  public static Inserts firstThisInstance(ListInsertable insertable) {
    List<Insert> inserts = insertable.getInserts();
    return new Inserts(inserts);
  }

  public Inserts andThenInto(String table) {
    Insert insert = Insert.into(table);
    setCurrent(insert);
    return this;
  }

  public Inserts andThenThisInstance(Insertable insertable) {
    Insert insert = insertable.getInsert();
    setCurrent(insert);
    return this;
  }

  public Inserts andThenThisInstance(ListInsertable insertable) {
    List<Insert> inserts = insertable.getInserts();
    this.inserts.addAll(inserts);
    this.current = null;
    return this;
  }

  public List<Insert> list() {
    return ImmutableList.copyOf(inserts);
  }

  @Override
  public Inserts value(String colName, BigDecimal value) {
    current.value(colName, value);
    return this;
  }

  @Override
  public Inserts value(String colName, Boolean value) {
    current.value(colName, value);
    return this;
  }

  @Override
  public Inserts value(String colName, Date value) {
    current.value(colName, value);
    return this;
  }

  @Override
  public Inserts value(String colName, DateTime value) {
    current.value(colName, value);
    return this;
  }

  @Override
  public Inserts value(String colName, Double value) {
    current.value(colName, value);
    return this;
  }

  @Override
  public Inserts value(String colName, Float value) {
    current.value(colName, value);
    return this;
  }

  @Override
  public Inserts value(String colName, Integer value) {
    current.value(colName, value);
    return this;
  }

  @Override
  public Inserts value(String colName, LocalDate value) {
    current.value(colName, value);
    return this;
  }

  @Override
  public Inserts value(String colName, Long value) {
    current.value(colName, value);
    return this;
  }

  @Override
  public Inserts value(String colName, String value) {
    current.value(colName, value);
    return this;
  }

  @Override
  public Inserts value(String colName, LazyParam<?> value) {
    current.value(colName, value);
    return this;
  }

  @Override
  public Inserts onGeneratedKey(GeneratedKeyCallback callback) {
    current.onGeneratedKey(callback);
    return this;
  }

}