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
package br.com.objectos.comuns.cnab.itau;

import org.joda.time.LocalDate;

import br.com.objectos.comuns.cnab.Itau;
import br.com.objectos.comuns.cnab.Lote;

import com.google.common.base.Function;

/**
 * @author marcos.piazzolla@objectos.com.br (Marcos Piazzolla)
 */
class LoteToDataOcorrencia implements Function<Lote, LocalDate> {
  @Override
  public LocalDate apply(Lote input) {
    return input.get(Itau.lote().dataDeOcorrencia());
  }
}