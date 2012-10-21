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
package br.com.objectos.comuns.sitebricks;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public enum LocalDateFormat {

  ISO("yyyy-MM-dd"),

  DD_MM_YY("dd/MM/yy"),

  DD_MM_YYYY("dd/MM/yyyy"),

  MM_DD_YYYY("MM/dd/yyyy");

  private final DateTimeFormatter formatter;

  private LocalDateFormat(String pattern) {
    this.formatter = DateTimeFormat.forPattern(pattern);
  }

  public LocalDate parse(String input) {
    try {
      return formatter.parseLocalDate(input);
    } catch (IllegalArgumentException e) {
      return null;
    } catch (NullPointerException e) {
      return null;
    }
  }

  public String format(LocalDate date) {
    return date != null ? formatter.print(date) : "";
  }

}