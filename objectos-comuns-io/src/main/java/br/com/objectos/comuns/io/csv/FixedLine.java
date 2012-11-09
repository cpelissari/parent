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
package br.com.objectos.comuns.io.csv;

import java.util.Map;

import br.com.objectos.comuns.io.Column;
import br.com.objectos.comuns.io.ColumnKey;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class FixedLine implements br.com.objectos.comuns.io.FixedLine {

  private final Map<ColumnKey<?>, CsvConverter<?>> converterMap;

  private final int number;

  private final String line;

  public FixedLine(Map<ColumnKey<?>, CsvConverter<?>> converterMap, int number, String line) {
    this.converterMap = converterMap;
    this.number = number;
    this.line = line;
  }

  @Override
  public int getNumber() {
    return number;
  }

  @Override
  public Column column(int index) {
    throw new UnsupportedOperationException("You should call column(int, int) instead.");
  }

  @Override
  public Column column(int start, int end) {
    String text = line.substring(start, end);
    return new CsvColumn(converterMap, text);
  }

  @Override
  public String getText() {
    return line;
  }

  @Override
  public String toString() {
    return String.format("%d:%s", number, getText());
  }

}