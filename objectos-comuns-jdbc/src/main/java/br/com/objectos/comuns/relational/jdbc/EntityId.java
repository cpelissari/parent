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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
abstract class EntityId<T> {

  static final EntityIdNoop NOOP = new EntityIdNoop();

  final String col;

  final T value;

  final GeneratedKeyListener listener;

  private EntityId(String col, T value, GeneratedKeyListener listener) {
    this.col = col;
    this.value = value;
    this.listener = listener;
  }

  public void bind(Insert insert) {
    insert.onGeneratedKey(new GeneratedKeyCallbackImpl(listener));
  }

  public void bind(Update update) {
    update.add("where %s = ?", col);

    List<ParamValue<?>> params = update.getParams();
    int size = params.size();
    params.add(getParam(size + 1));
  }

  public void bind(Delete delete) {
    delete.add("where %s = ?", col);

    List<ParamValue<?>> params = delete.getParams();
    int size = params.size();
    params.add(getParam(size + 1));
  }

  abstract ParamValue<T> getParam(int index);

  static EntityIdInt valueOf(String col, Integer value, GeneratedKeyListener listener) {
    return new EntityIdInt(col, value, listener);
  }
  static EntityIdLong valueOf(String col, Long value, GeneratedKeyListener listener) {
    return new EntityIdLong(col, value, listener);
  }
  static EntityIdString valueOf(String col, String value, GeneratedKeyListener listener) {
    return new EntityIdString(col, value, listener);
  }

  static class EntityIdNoop extends EntityId<Object> {
    public EntityIdNoop() {
      super(null, null, null);
    }

    @Override
    public void bind(Insert insert) {
    }

    @Override
    ParamValue<Object> getParam(int index) {
      return null;
    }
  }

  static class EntityIdInt extends EntityId<Integer> {
    public EntityIdInt(String col, Integer value, GeneratedKeyListener listener) {
      super(col, value, listener);
    }

    @Override
    ParamValue<Integer> getParam(int index) {
      return new ParamInt(index, value);
    }
  }

  static class EntityIdLong extends EntityId<Long> {
    public EntityIdLong(String col, Long value, GeneratedKeyListener listener) {
      super(col, value, listener);
    }

    @Override
    ParamValue<Long> getParam(int index) {
      return new ParamLong(index, value);
    }
  }

  static class EntityIdString extends EntityId<String> {
    public EntityIdString(String col, String value, GeneratedKeyListener listener) {
      super(col, value, listener);
    }

    @Override
    ParamValue<String> getParam(int index) {
      return new ParamString(index, value);
    }
  }

  private static class GeneratedKeyCallbackImpl implements GeneratedKeyCallback {

    private final GeneratedKeyListener listener;

    public GeneratedKeyCallbackImpl(GeneratedKeyListener listener) {
      this.listener = listener;
    }

    @Override
    public void set(ResultSet rs) throws SQLException {
      if (rs.next()) {
        listener.set(new GeneratedKeyImpl(rs));
      }
    }

  }

  private static class GeneratedKeyImpl implements GeneratedKey {

    private final ResultSet rs;

    public GeneratedKeyImpl(ResultSet rs) {
      this.rs = rs;
    }

    @Override
    public int getInt() {
      return getInt(1);
    }
    @Override
    public int getInt(int index) {
      try {
        return rs.getInt(index);
      } catch (SQLException e) {
        throw new SQLRuntimeException(e);
      }
    }

    @Override
    public long getLong() {
      return getLong(1);
    }
    @Override
    public long getLong(int index) {
      try {
        return rs.getLong(index);
      } catch (SQLException e) {
        throw new SQLRuntimeException(e);
      }
    }

    @Override
    public String getString() {
      return getString(1);
    }
    @Override
    public String getString(int index) {
      try {
        return rs.getString(index);
      } catch (SQLException e) {
        throw new SQLRuntimeException(e);
      }
    }

  }

}