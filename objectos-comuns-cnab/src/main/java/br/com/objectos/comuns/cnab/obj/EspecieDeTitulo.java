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
package br.com.objectos.comuns.cnab.obj;

import static com.google.common.collect.Maps.newHashMap;

import java.util.EnumSet;
import java.util.Map;

import br.com.objectos.comuns.cnab.RemessaEnum;

/**
 * @author marcos.piazzolla@objectos.com.br (Marcos Piazzolla)
 */
public enum EspecieDeTitulo implements RemessaEnum {

  DUPLICATA(1),

  NOTA_PROMISSORIA(2),

  NOTA_SEGURO(3),

  COBRANCA_SERIADA(4),

  RECIBO(5),

  LETRA_DE_CAMBIO(10),

  NOTA_DE_DEBITO(11),

  DUPLICATA_DE_SERV(12),

  OUTROS(99);

  private static final Map<Integer, EspecieDeTitulo> codigoMap = newHashMap();

  static {
    EnumSet<EspecieDeTitulo> especies = EnumSet.allOf(EspecieDeTitulo.class);
    for (EspecieDeTitulo especie : especies) {
      codigoMap.put(especie.valor, especie);
    }
  }

  private int valor;

  private EspecieDeTitulo(int valor) {
    this.valor = valor;
  }

  public static EspecieDeTitulo valueOf(int codigo) {
    return codigoMap.get(codigo);
  }

  @Override
  public String getValor() {
    return String.format("%02d", valor);
  }

}