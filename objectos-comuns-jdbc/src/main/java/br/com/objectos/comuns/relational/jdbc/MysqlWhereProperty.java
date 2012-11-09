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

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class MysqlWhereProperty extends AnsiWhereProperty {

  public MysqlWhereProperty(String tableAlias, String property) {
    super(tableAlias, property);
  }

  @Override
  public void like(String value) {
    this.value = value;
    if (isSet(value)) {
      where = String.format("%s like concat('%%', ?, '%%')", getProperty());
    }
  }

  @Override
  public void endsWith(String value) {
    this.value = value;
    if (isSet(value)) {
      where = String.format("%s like concat('%%', ?)", getProperty());
    }
  }

  @Override
  public void startsWith(String value) {
    this.value = value;
    if (isSet(value)) {
      where = String.format("%s like concat(?, '%%')", getProperty());
    }
  }

  @Override
  protected String getProperty() {
    String property = super.getProperty();
    Iterable<String> parts = Splitter.on('.').split(property);
    Iterable<String> escaped = Iterables.transform(parts, new Escape());
    return Joiner.on(".").join(escaped);
  }

  private class Escape implements Function<String, String> {
    @Override
    public String apply(String input) {
      return '`' + input + '`';
    }
  }

}