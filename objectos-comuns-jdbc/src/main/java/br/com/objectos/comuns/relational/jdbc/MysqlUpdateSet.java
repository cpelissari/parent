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
class MysqlUpdateSet extends UpdateSet {

  public MysqlUpdateSet(String colName, Object value) {
    super(colName, value);
  }

  @Override
  public String toString() {
    Iterable<String> parts = Splitter.on('.').split(colName);
    Iterable<String> cols = Iterables.transform(parts, new ColumnEscaper());
    String _colName = Joiner.on('.').join(cols);
    return String.format("%s = ?", _colName);
  }

  private class ColumnEscaper implements Function<String, String> {
    @Override
    public String apply(String input) {
      return '`' + input + '`';
    }
  }

}