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
package br.com.objectos.comuns.io.xls;

import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import br.com.objectos.comuns.io.ColumnKey;
import br.com.objectos.comuns.io.Line;
import br.com.objectos.comuns.io.ParsedLines;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class XlsParsedLines implements ParsedLines {

  private final Map<ColumnKey<?>, XlsConverter<?>> converterMap;

  private final Sheet sheet;

  private final int skipLines;

  public XlsParsedLines(Map<ColumnKey<?>, XlsConverter<?>> converterMap, Sheet sheet, int skipLines) {
    this.converterMap = converterMap;
    this.sheet = sheet;
    this.skipLines = skipLines;
  }

  @Override
  public Iterator<Line> iterator() {
    return new Iterador();
  }

  private class Iterador implements Iterator<Line> {

    private int counter = skipLines;

    private Row currentRow;

    @Override
    public boolean hasNext() {
      currentRow = sheet.getRow(counter);
      return currentRow != null;
    }

    @Override
    public Line next() {
      return currentRow != null ? nextRow() : null;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    private Line nextRow() {
      counter++;
      return new XlsLine(converterMap, counter, currentRow);
    }

  }

}