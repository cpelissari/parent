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

import org.joda.time.LocalDate;

import br.com.objectos.comuns.base.br.Estado;
import br.com.objectos.comuns.util.Booleano;

/**
 * @author thalita.palhares@objectos.com.br (Thalita Palhares)
 */
class Defaults {

  static final CsvConverter<Boolean> BOOLEAN = new BooleanCsvConverter("true", "false");
  static final CsvConverter<Booleano> BOOLEANO = new BooleanoCsvConverter("true", "false");
  static final CsvConverter<Double> DOUBLE = new DoubleCsvConverter();
  static final CsvConverter<Estado> ESTADO = new EstadoCsvConverter();
  static final CsvConverter<Integer> INTEGER = new IntegerCsvConverter();
  static final CsvConverter<LocalDate> LOCAL_DATE = new LocalDateCsvConverter();
  static final CsvConverter<Long> LONG = new LongCsvConverter();
  static final CsvConverter<String> STRING = new StringCsvConverter();

  static final char PONTO_VIRGULA = ';';

  static final char TAB = '\t';

  static final char VIRGULA = ',';

  private Defaults() {
  }

}