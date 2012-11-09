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

import static com.google.common.collect.Lists.newArrayList;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import br.com.objectos.comuns.io.ColumnKey;
import br.com.objectos.comuns.io.ComunsIOException;
import br.com.objectos.comuns.io.FilterBlankLines;
import br.com.objectos.comuns.io.Line;
import br.com.objectos.comuns.io.ParsedLines;

import com.google.common.collect.Iterators;

/**
 * @author aline.heredia@objectos.com.br (Aline Heredia)
 */
class CsvParsedLines implements ParsedLines {

  private final Map<ColumnKey<?>, CsvConverter<?>> converterMap;

  private final CSVReader reader;

  private final List<ComunsIOException> exceptions = newArrayList();

  public CsvParsedLines(Map<ColumnKey<?>, CsvConverter<?>> converterMap, CSVReader reader) {
    this.converterMap = converterMap;
    this.reader = reader;
  }

  @Override
  public Iterator<Line> iterator() {
    LineIterator iterator = new LineIterator();
    return Iterators.filter(iterator, new FilterBlankLines());
  }

  private class LineIterator implements Iterator<Line> {

    private int counter = 0;

    private String[] currentLine;

    @Override
    public boolean hasNext() {
      try {

        computeLine();
        return currentLine != null;

      } catch (IOException e) {

        exceptions.add(new ComunsIOException(e));
        return false;

      }
    }

    @Override
    public Line next() {
      return currentLine != null ? nextLine() : null;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    private void computeLine() throws IOException {
      if (currentLine == null) {
        currentLine = reader.readNext();
      }
    }

    private Line nextLine() {
      counter++;
      Line line = new CsvLine(converterMap, counter, currentLine);

      currentLine = null;

      return line;
    }

  }

}