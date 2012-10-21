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

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import br.com.objectos.comuns.relational.jdbc.UpdateBuilder.UpdateAlias;
import br.com.objectos.comuns.relational.search.Join;
import br.com.objectos.comuns.relational.search.OrderProperty;
import br.com.objectos.comuns.relational.search.WhereProperty;
import br.com.objectos.comuns.relational.search.WhereThis;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class UpdateImpl implements UpdateDeprecated {

  private final UpdateBuilder update;

  private final JdbcUpdateExec exec;

  public UpdateImpl(UpdateBuilder update, JdbcUpdateExec exec) {
    this.update = update;
    this.exec = exec;
  }

  @Override
  public int execute() {
    return exec.execute(update);
  }

  @Override
  public UpdateAlias update(String string) {
    return update.update(string);
  }

  @Override
  public void clearOrders() {
    update.clearOrders();
  }

  @Override
  public Join join(String relationship, String alias) {
    return update.join(relationship, alias);
  }

  @Override
  public Join leftJoin(String relationship, String alias) {
    return update.leftJoin(relationship, alias);
  }

  @Override
  public UpdateSetter set(String colName, BigDecimal value) {
    return update.set(colName, value);
  }

  @Override
  public WhereThis whereThis() {
    return update.whereThis();
  }

  @Override
  public UpdateSetter set(String colName, Boolean value) {
    return update.set(colName, value);
  }

  @Override
  public WhereProperty where(String propertyName) {
    return update.where(propertyName);
  }

  @Override
  public UpdateSetter set(String colName, Date value) {
    return update.set(colName, value);
  }

  @Override
  public OrderProperty order(String propertyName) {
    return update.order(propertyName);
  }

  @Override
  public UpdateSetter set(String colName, DateTime value) {
    return update.set(colName, value);
  }

  @Override
  public UpdateSetter set(String colName, Double value) {
    return update.set(colName, value);
  }

  @Override
  public UpdateSetter set(String colName, Float value) {
    return update.set(colName, value);
  }

  @Override
  public UpdateSetter set(String colName, Integer value) {
    return update.set(colName, value);
  }

  @Override
  public UpdateSetter set(String colName, LocalDate value) {
    return update.set(colName, value);
  }

  @Override
  public UpdateSetter set(String colName, Long value) {
    return update.set(colName, value);
  }

  @Override
  public UpdateSetter set(String colName, String value) {
    return update.set(colName, value);
  }

}