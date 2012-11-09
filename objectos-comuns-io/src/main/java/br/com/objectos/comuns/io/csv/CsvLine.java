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
package br.com.objectos.comuns.io.csv;

import java.util.Arrays;
import java.util.Map;

import br.com.objectos.comuns.io.Column;
import br.com.objectos.comuns.io.ColumnKey;
import br.com.objectos.comuns.io.IllegalColumnException;
import br.com.objectos.comuns.io.Line;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class CsvLine implements Line {

  private final Map<ColumnKey<?>, CsvConverter<?>> converterMap;

  private final int number;

  private final String[] columns;

  public CsvLine(Map<ColumnKey<?>, CsvConverter<?>> converterMap, int number, String[] columns) {
    this.converterMap = converterMap;
    this.number = number;
    this.columns = columns;
  }

  @Override
  public int getNumber() {
    return number;
  }

  @Override
  public Column column(int indice) {
    String texto = getTextAt(indice);
    return new CsvColumn(converterMap, texto);
  }

  @Override
  public String getText() {
    return Arrays.toString(columns);
  }

  @Override
  public String toString() {
    return String.format("%d:%s", number, getText());
  }

  private String getTextAt(int index) {
    try {
      return columns[index];
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalColumnException(index, columns.length);
    }
  }

}