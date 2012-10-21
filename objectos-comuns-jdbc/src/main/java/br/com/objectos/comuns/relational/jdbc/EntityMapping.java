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

import static com.google.common.collect.Maps.newLinkedHashMap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class EntityMapping {

  private final String table;

  private final Map<String, ParamValue<?>> values = newLinkedHashMap();

  private EntityId<?> id = EntityId.NOOP;

  EntityMapping(String table) {
    this.table = table;
  }

  Insertable toInsertable() {
    return new Insertable() {
      @Override
      public Insert getInsert() {
        Insert insert = Insert.into(table);
        insert.values.putAll(values);

        id.bind(insert);

        return insert;
      }
    };
  }

  Updatable toUpdatable() {
    return new Updatable() {
      @Override
      public Update getUpdate() {
        String params = Joiner.on("= ?, ").join(values.keySet());

        Update update = Update.get()
            .add("update %s", table)
            .add("set")
            .add(params + "= ?");

        update.getParams().addAll(values.values());

        id.bind(update);

        return update;
      }
    };
  }

  Deletable toDeletable() {
    return new Deletable() {
      @Override
      public Delete getDelete() {
        Delete delete = Delete.get()
            .add("delete from %s", table);

        id.bind(delete);

        return delete;
      }
    };
  }

  public EntityMapping id(String col, int id, GeneratedKeyListener listener) {
    Preconditions.checkState(this.id == EntityId.NOOP, "id was already assigned");
    this.id = EntityId.valueOf(col, id, listener);
    return this;
  }
  public EntityMapping id(String col, long id, GeneratedKeyListener listener) {
    Preconditions.checkState(this.id == EntityId.NOOP, "id was already assigned");
    this.id = EntityId.valueOf(col, id, listener);
    return this;
  }
  public EntityMapping id(String col, String id, GeneratedKeyListener listener) {
    Preconditions.checkState(this.id == EntityId.NOOP, "id was already assigned");
    this.id = EntityId.valueOf(col, id, listener);
    return this;
  }

  public EntityMapping col(String col, boolean booleanValue) {
    return addValue(col, new ParamBoolean(next(), booleanValue));
  }
  public EntityMapping col(String col, Boolean booleanValue) {
    return addValue(col, new ParamBoolean(next(), booleanValue));
  }

  public EntityMapping col(String col, int intValue) {
    return addValue(col, new ParamInt(next(), intValue));
  }
  public EntityMapping col(String col, Integer intValue) {
    return addValue(col, new ParamInt(next(), intValue));
  }

  public EntityMapping col(String col, long longValue) {
    return addValue(col, new ParamLong(next(), longValue));
  }
  public EntityMapping col(String col, Long longValue) {
    return addValue(col, new ParamLong(next(), longValue));
  }

  public EntityMapping col(String col, float floatValue) {
    return addValue(col, new ParamFloat(next(), floatValue));
  }
  public EntityMapping col(String col, Float floatValue) {
    return addValue(col, new ParamFloat(next(), floatValue));
  }

  public EntityMapping col(String col, double doubleValue) {
    return addValue(col, new ParamDouble(next(), doubleValue));
  }
  public EntityMapping col(String col, Double doubleValue) {
    return addValue(col, new ParamDouble(next(), doubleValue));
  }

  public EntityMapping col(String col, BigDecimal bigDecimal) {
    return addValue(col, new ParamBigDecimal(next(), bigDecimal));
  }

  public EntityMapping col(String col, Date date) {
    return addValue(col, new ParamDate(next(), date));
  }

  public EntityMapping col(String col, LocalDate localDate) {
    return addValue(col, new ParamLocalDate(next(), localDate));
  }

  public EntityMapping col(String col, DateTime dateTime) {
    return addValue(col, new ParamDateTime(next(), dateTime));
  }

  public EntityMapping col(String col, String text) {
    return addValue(col, new ParamString(next(), text));
  }

  private EntityMapping addValue(String colName, ParamValue<?> val) {
    values.put(colName, val);
    return this;
  }

  private int next() {
    return values.size() + 1;
  }

}