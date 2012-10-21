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

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author aline.heredia@objectos.com.br (Aline Heredia)
 */
public class LocalDateCsvConverter extends AbstractCsvConverter<LocalDate> {

  private final DateTimeFormatter format;

  public LocalDateCsvConverter() {
    this("yyyy-MM-dd");
  }

  public LocalDateCsvConverter(String format) {
    this.format = DateTimeFormat.forPattern(format);
  }

  @Override
  public LocalDate convert(String text) {
    DateTime dateTime = format.parseDateTime(text);
    return new LocalDate(dateTime);
  }

}