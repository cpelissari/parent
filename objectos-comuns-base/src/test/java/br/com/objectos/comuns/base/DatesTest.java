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
package br.com.objectos.comuns.base;

import static br.com.objectos.comuns.base.Dates.newLocalDate;
import static br.com.objectos.comuns.base.Dates.toXML;
import static org.testng.Assert.assertEquals;

import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.LocalDate;
import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class DatesTest {

  public void verifiqueNewLocalDate() {
    LocalDate NATAL_2009 = newLocalDate(2009, 12, 25);

    assertEquals(NATAL_2009.toString(), "2009-12-25");
  }

  public void verifiqueToXML() {
    XMLGregorianCalendar xml = toXML(NATAL_2009());

    assertEquals(xml.toString(), "2009-12-25T00:00:00");
  }

  private LocalDate NATAL_2009() {
    return newLocalDate(2009, 12, 25);
  }

}