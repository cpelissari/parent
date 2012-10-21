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

import static com.google.common.collect.Maps.newLinkedHashMap;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Insert extends NativeSqlBuilder<Insert> implements HasInsertMethods<Insert> {

  final String table;

  final Map<String, ParamValue<?>> values = newLinkedHashMap();

  GeneratedKeyCallback keyCallback;

  private Insert() {
    this.table = null;
  }

  Insert(String table) {
    this.table = table;
  }

  public static Insert get() {
    return new Insert();
  }

  @Override
  public List<String> getParts() {
    String part = toString();
    return ImmutableList.of(part);
  }

  @Override
  public List<ParamValue<?>> getParams() {
    Collection<ParamValue<?>> paramsValures = values.values();
    return ImmutableList.copyOf(paramsValures);
  }

  @Override
  Insert getSelf() {
    return this;
  }

  public static Insert into(String table) {
    return new Insert(table);
  }

  @Override
  public Insert value(String colName, BigDecimal value) {
    return addValue(colName, new ParamBigDecimal(next(), value));
  }

  @Override
  public Insert value(String colName, Boolean value) {
    return addValue(colName, new ParamBoolean(next(), value));
  }

  @Override
  public Insert value(String colName, java.util.Date value) {
    return addValue(colName, new ParamDate(next(), value));
  }

  @Override
  public Insert value(String colName, DateTime value) {
    return addValue(colName, new ParamDateTime(next(), value));
  }

  @Override
  public Insert value(String colName, Double value) {
    return addValue(colName, new ParamDouble(next(), value));
  }

  @Override
  public Insert value(String colName, Float value) {
    return addValue(colName, new ParamFloat(next(), value));
  }

  @Override
  public Insert value(String colName, Integer value) {
    return addValue(colName, new ParamInt(next(), value));
  }

  @Override
  public Insert value(String colName, LocalDate value) {
    return addValue(colName, new ParamLocalDate(next(), value));
  }

  @Override
  public Insert value(String colName, Long value) {
    return addValue(colName, new ParamLong(next(), value));
  }

  @Override
  public Insert value(String colName, String value) {
    return addValue(colName, new ParamString(next(), value));
  }

  @Override
  public Insert value(String colName, LazyParam<?> lazyParam) {
    return addValue(colName, new ParamLazy(next(), lazyParam));
  }

  @Override
  public Insert onGeneratedKey(GeneratedKeyCallback callback) {
    this.keyCallback = callback;
    return this;
  }

  public void prepare(Stmt stmt) {
    Collection<ParamValue<?>> vals = values.values();
    List<ParamValue<?>> params = ImmutableList.copyOf(vals);

    for (ParamValue<?> param : params) {
      param.set(stmt);
    }
  }

  public String toUpdate(PrimaryKey primaryKey) {
    Set<String> keys = values.keySet();
    Iterable<String> escapedCols = Iterables.transform(keys, new ToUpdate());
    String columns = Joiner.on(", ").join(escapedCols);

    String keyColumn = primaryKey.getColumn();
    Object val = primaryKey.getValue();
    addValue(keyColumn, ParamValue.valueOf(next(), val));

    return String.format("update %s set %s where `%s`=?", table, columns, primaryKey.getColumn());
  }

  @Override
  public String toString() {
    Set<String> keys = values.keySet();
    Iterable<String> escapedCols = Iterables.transform(keys, new ColumnEscaper());
    String columns = Joiner.on(", ").join(escapedCols);

    Object[] argArr = new Object[keys.size()];
    Arrays.fill(argArr, "?");
    String args = Joiner.on(", ").join(argArr);

    return String.format("INSERT INTO %s (%s) VALUES (%s)", table, columns, args);
  }

  GeneratedKeyCallback getKeyCallback() {
    return keyCallback;
  }

  private int next() {
    return values.size() + 1;
  }

  private Insert addValue(String colName, ParamValue<?> val) {
    values.put(colName, val);
    return this;
  }

  private class ColumnEscaper implements Function<String, String> {
    @Override
    public String apply(String input) {
      return '`' + input + '`';
    }
  }

  private class ToUpdate implements Function<String, String> {
    @Override
    public String apply(String input) {
      return String.format("`%s`=?", input);
    }
  }

}