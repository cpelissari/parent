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

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class NativeSqlBuilder<SQL extends NativeSqlBuilder<SQL>> {

  public static interface AddIf<SQL> {
    SQL paramNotNull(Object param);
  }

  private final List<String> parts = newArrayList();
  private final List<ParamValue<?>> params = newArrayList();

  private int index;

  NativeSqlBuilder() {
  }

  public SQL add(String string) {
    Preconditions.checkNotNull(string, "SQL part cannot be null");
    parts.add(string);

    return getSelf();
  }

  public SQL add(String template, Object... args) {
    Preconditions.checkNotNull(template, "template cannot be null");
    String string = String.format(template, args);
    return add(string);
  }

  public AddIf<SQL> addIf(final String string) {
    return new AddIf<SQL>() {
      @Override
      public SQL paramNotNull(Object param) {
        if (param != null && !"".equals(param)) {
          add(string);
          param(param);
        }

        return NativeSqlBuilder.this.getSelf();
      }
    };
  }

  public AddIf<SQL> addIf(String template, Object... args) {
    Preconditions.checkNotNull(template, "template cannot be null");
    String string = String.format(template, args);
    return addIf(string);
  }

  public SQL param(Object value) {
    Preconditions.checkNotNull(value, "param cannot be null");

    ParamValue<?> param = ParamValue.valueOf(++index, value);
    params.add(param);

    return getSelf();
  }

  abstract SQL getSelf();

  public List<String> getParts() {
    return parts;
  }

  public List<ParamValue<?>> getParams() {
    return params;
  }

}