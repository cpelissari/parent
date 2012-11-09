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

import br.com.objectos.comuns.base.br.Estado;
import br.com.objectos.comuns.io.ColumnKey;
import br.com.objectos.comuns.io.ComunsIOException;
import br.com.objectos.comuns.io.Encoding;
import br.com.objectos.comuns.io.ParsedFixedLines;
import br.com.objectos.comuns.util.Booleano;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class FixedFile implements ParsedFixedLines.Builder {

  private final Map<ColumnKey<?>, CsvConverter<?>> converterMap = newHashMap();

  private final InputStream inputStream;

  private final Reader reader;

  private Encoding encoding = Encoding.UTF_8;

  private int skipLines = 0;

  private FixedFile(InputStream inputStream) {
    this.inputStream = inputStream;
    this.reader = null;

    bindDefaultConverters();
  }

  private FixedFile(File file) {
    try {
      this.inputStream = new FileInputStream(file);
      this.reader = null;

      bindDefaultConverters();
    } catch (FileNotFoundException e) {
      String msg = String.format("Cannot build CSV file. File %s does not exist.", file);
      throw new IllegalArgumentException(msg, e);
    }
  }

  private FixedFile(Reader reader) {
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

  public static FixedFile parse(InputStream inputStream) {
    return new FixedFile(inputStream);
  }
  public static FixedFile parse(File file) {
    return new FixedFile(file);
  }
  public static FixedFile parseReader(Reader reader) {
    return new FixedFile(reader);
  }
  public static FixedFile parseString(String text) {
    String sep = System.getProperty("line.separator");
    text = text + sep + " ";

    StringReader reader = new StringReader(text);
    return new FixedFile(reader);
  }

  @Override
  public ParsedFixedLines getLines() {
    Charset charset = encoding.toCharset();

    BufferedReader reader;

    if (inputStream != null) {
      InputStreamReader streamReader = new InputStreamReader(inputStream, charset);
      reader = new BufferedReader(streamReader);

    } else {
      reader = new BufferedReader(this.reader);

    }

    skipLines(reader);

    return new FixedParsedLines(converterMap, reader);
  }

  public <T> FixedFile withConverter(ColumnKey<T> key, CsvConverter<T> converter) {
    this.converterMap.put(key, converter);
    return this;
  }
  public <T> FixedFile withConverter(Class<T> type, CsvConverter<T> converter) {
    return withConverter(ColumnKey.of(type), converter);
  }

  public FixedFile encodedWith(Encoding encoding) {
    this.encoding = encoding;
    return this;
  }

  public FixedFile skipFirstLines(int lines) {
    this.skipLines = lines;
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