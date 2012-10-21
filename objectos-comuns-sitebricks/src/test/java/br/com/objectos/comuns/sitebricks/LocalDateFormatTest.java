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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.joda.time.LocalDate;
import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class LocalDateFormatTest {

  public void default_iso_format() {
    LocalDateFormat format = LocalDateFormat.ISO;
    LocalDate date = new LocalDate(2012, 8, 10);
    String expected = "2012-08-10";

    verify(format, date, expected);
  }

  public void dd_mm_yyyy() {
    LocalDateFormat format = LocalDateFormat.DD_MM_YYYY;
    LocalDate date = new LocalDate(2012, 8, 10);
    String expected = "10/08/2012";

    verify(format, date, expected);
  }

  public void mm_dd_yyyy() {
    LocalDateFormat format = LocalDateFormat.MM_DD_YYYY;
    LocalDate date = new LocalDate(2012, 8, 10);
    String expected = "08/10/2012";

    verify(format, date, expected);
  }

  public void parse_null_should_return_null() {
    LocalDateFormat format = LocalDateFormat.ISO;
    LocalDate res = format.parse(null);
    assertThat(res, is(nullValue()));
  }

  public void format_null_should_return_empty() {
    LocalDateFormat format = LocalDateFormat.ISO;
    String res = format.format(null);
    assertThat(res, equalTo(""));
  }

  private void verify(LocalDateFormat format, LocalDate date, String expected) {
    String input = format.format(date);
    assertThat(input, equalTo(expected));

    LocalDate res = format.parse(input);

    assertThat(res, equalTo(date));
  }

}