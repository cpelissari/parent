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

import static com.google.common.collect.Maps.newHashMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Map;

import org.joda.time.LocalDate;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import br.com.objectos.comuns.base.br.Estado;
import br.com.objectos.comuns.io.ColumnKey;
import br.com.objectos.comuns.io.ComunsIOException;
import br.com.objectos.comuns.io.Encoding;
import br.com.objectos.comuns.io.ParsedLines;
import br.com.objectos.comuns.util.Booleano;

/**
 * @author aline.heredia@objectos.com.br (Aline Heredia)
 */
public class CsvFile implements ParsedLines.Builder {

  private final Map<ColumnKey<?>, CsvConverter<?>> converterMap = newHashMap();

  private final InputStream inputStream;

  private final Reader reader;

  private char csvChar = Defaults.VIRGULA;

  private char quotes = CSVParser.DEFAULT_QUOTE_CHARACTER;

  private char escape = CSVParser.DEFAULT_ESCAPE_CHARACTER;

  private Encoding encoding = Encoding.UTF_8;

  private int skipLines = 0;

  private CsvFile(InputStream inputStream) {
    this.inputStream = inputStream;
    this.reader = null;

    bindDefaultConverters();
  }

  private CsvFile(File file) {
    try {
      this.inputStream = new FileInputStream(file);
      this.reader = null;

      bindDefaultConverters();
    } catch (FileNotFoundException e) {
      String msg = String.format("Cannot build CSV file. File %s does not exist.", file);
      throw new IllegalArgumentException(msg, e);
    }
  }

  private CsvFile(Reader reader) {
    this.inputStream = null;
    this.reader = reader;

    bindDefaultConverters();
  }

  private void bindDefaultConverters() {
    converterMap.put(ColumnKey.of(Boolean.class), Defaults.BOOLEAN);
    converterMap.put(ColumnKey.of(Booleano.class), Defaults.BOOLEANO);
    converterMap.put(ColumnKey.of(Double.class), Defaults.DOUBLE);
    converterMap.put(ColumnKey.of(Estado.class), Defaults.ESTADO);
    converterMap.put(ColumnKey.of(Integer.class), Defaults.INTEGER);
    converterMap.put(ColumnKey.of(LocalDate.class), Defaults.LOCAL_DATE);
    converterMap.put(ColumnKey.of(Long.class), Defaults.LONG);
    converterMap.put(ColumnKey.of(String.class), Defaults.STRING);
  }

  public static CsvFile parse(InputStream inputStream) {
    return new CsvFile(inputStream);
  }
  public static CsvFile parse(File file) {
    return new CsvFile(file);
  }
  public static CsvFile parseReader(Reader reader) {
    return new CsvFile(reader);
  }
  public static CsvFile parseString(String text) {
    StringReader reader = new StringReader(text);
    return new CsvFile(reader);
  }

  @Override
  public ParsedLines getLines() {
    Charset charset = encoding.toCharset();

    BufferedReader reader;

    if (inputStream != null) {
      InputStreamReader streamReader = new InputStreamReader(inputStream, charset);
      reader = new BufferedReader(streamReader);

    } else {
      reader = new BufferedReader(this.reader);

    }

    skipLines(reader);

    CSVReader csv = new CSVReader(reader, csvChar, quotes, escape, 0);

    return new CsvParsedLines(converterMap, csv);
  }

  public <T> CsvFile withConverter(ColumnKey<T> key, CsvConverter<T> converter) {
    this.converterMap.put(key, converter);
    return this;
  }
  public <T> CsvFile withConverter(Class<T> type, CsvConverter<T> converter) {
    return withConverter(ColumnKey.of(type), converter);
  }

  public CsvFile encodedWith(Encoding encoding) {
    this.encoding = encoding;
    return this;
  }

  public CsvFile skipFirstLines(int lines) {
    this.skipLines = lines;
    return this;
  }

  public CsvFile notEscaped() {
    this.escape = CSVWriter.NO_ESCAPE_CHARACTER;
    return this;
  }

  public CsvFile notQuoted() {
    this.quotes = CSVWriter.NO_QUOTE_CHARACTER;
    return this;
  }

  public CsvFile on(char separator) {
    this.csvChar = separator;
    return this;
  }

  public CsvFile onSemicolons() {
    this.csvChar = Defaults.PONTO_VIRGULA;
    return this;
  }

  public CsvFile onTabs() {
    this.csvChar = Defaults.TAB;
    return this;
  }

  public CsvFile onCommas() {
    this.csvChar = Defaults.VIRGULA;
    return this;
  }

  private void skipLines(BufferedReader reader) {
    try {
      for (int i = 0; i < skipLines; i++) {
        reader.readLine();
      }
    } catch (IOException e) {
      throw new ComunsIOException(e);
    }
  }

}