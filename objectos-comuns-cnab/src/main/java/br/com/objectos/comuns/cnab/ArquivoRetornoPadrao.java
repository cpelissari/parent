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
package br.com.objectos.comuns.cnab;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ArquivoRetornoPadrao implements ArquivoRetorno {

  private final Banco banco;

  private final Header header;

  private final List<Lote> lotes = newArrayList();

  public ArquivoRetornoPadrao(Header header, Iterable<Registro> registros) {
    this.banco = header.getBanco();
    this.header = header;

    registros = Iterables.filter(registros, Predicates.notNull());

    for (Registro registro : registros) {
      RegistroTipo tipo = registro.getTipo();
      switch (tipo) {
      case HEADER:
        break;

      case LOTE:
        Lote lote = Lote.class.cast(registro);
        this.lotes.add(lote);
        break;

      case TRAILER:
        break;
      }
    }
  }

  @Override
  public Banco getBanco() {
    return banco;
  }

  @Override
  public Header getHeader() {
    return header;
  }

  @Override
  public List<Lote> getLotes() {
    return lotes;
  }

}