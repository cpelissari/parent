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

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @author thalita.palhares@objectos.com.br (Thalita Palhares)
 */
public class LongCsvConverter extends AbstractCsvConverter<Long> {

  @Override
  public Long convert(String text) {
    try {
      NumberFormat format = NumberFormat.getIntegerInstance();
      Number number = format.parse(text);
      return new Long(number.longValue());
    } catch (ParseException e) {
      throw newException(text);
    }
  }

}