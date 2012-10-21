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

import static com.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.LocalDate;

import br.com.objectos.comuns.base.br.Estado;
import br.com.objectos.comuns.io.ColumnKey;
import br.com.objectos.comuns.io.ComunsIOException;
import br.com.objectos.comuns.io.ParsedLines;
import br.com.objectos.comuns.util.Booleano;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class XlsFile implements ParsedLines.Builder {

  private static final XlsConverter<Boolean> BOOLEAN = new BooleanXlsConverter("true", "false");
  private static final XlsConverter<Booleano> BOOLEANO = new BooleanoXlsConverter("true", "false");
  private static final XlsConverter<Double> DOUBLE = new DoubleXlsConverter();
  private static final XlsConverter<Estado> ESTADO = new EstadoXlsConverter();
  private static final XlsConverter<Integer> INTEGER = new IntegerXlsConverter();
  private static final XlsConverter<LocalDate> LOCAL_DATE = new LocalDateXlsConverter();
  private static final XlsConverter<Long> LONG = new LongXlsConverter();
  private static final XlsConverter<String> STRING = new StringXlsConverter();

  private final Map<ColumnKey<?>, XlsConverter<?>> converterMap = newHashMap();

  private final InputStream inputStream;

  private int sheet = 0;

  private int skipLines = 0;

  private XlsFile(InputStream inputStream) {
    this.inputStream = inputStream;

    bindDefaultConverters();
  }

  private void bindDefaultConverters() {
    converterMap.put(ColumnKey.of(Boolean.class), BOOLEAN);
    converterMap.put(ColumnKey.of(Booleano.class), BOOLEANO);
    converterMap.put(ColumnKey.of(Double.class), DOUBLE);
    converterMap.put(ColumnKey.of(Estado.class), ESTADO);
    converterMap.put(ColumnKey.of(Integer.class), INTEGER);
    converterMap.put(ColumnKey.of(LocalDate.class), LOCAL_DATE);
    converterMap.put(ColumnKey.of(Long.class), LONG);
    converterMap.put(ColumnKey.of(String.class), STRING);
  }

  public static XlsFile parse(InputStream inputStream) {
    return new XlsFile(inputStream);
  }
  public static XlsFile parse(File file) {
    try {
      return new XlsFile(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      String msg = String.format("Cannot build XlsFile. File %s does not exist.", file);
      throw new IllegalArgumentException(msg, e);
    }
  }

  @Override
  public ParsedLines getLines() {
    Sheet sheet = getPlanilha();
    return new XlsParsedLines(converterMap, sheet, skipLines);
  }

  public <T> XlsFile withConverter(ColumnKey<T> key, XlsConverter<T> converter) {
    this.converterMap.put(key, converter);
    return this;
  }
  public <T> XlsFile withConverter(Class<T> type, XlsConverter<T> converter) {
    return withConverter(ColumnKey.of(type), converter);
  }

  public XlsFile skipFirstLines(int skipLines) {
    this.skipLines = skipLines;
    return this;
  }

  public XlsFile withSheetIndex(int index) {
    this.sheet = index;
    return this;
  }

  private Sheet getPlanilha() {
    try {

      Workbook workbook = new HSSFWorkbook(inputStream);
      return workbook.getSheetAt(sheet);

    } catch (IOException e) {
      throw new ComunsIOException(e);
    }
  }

}