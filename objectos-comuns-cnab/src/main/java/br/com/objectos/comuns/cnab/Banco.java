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

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public enum Banco {

  BRADESCO(237, Bradesco.banco),

  ITAU(341, Itau.banco),

  OUTROS(999, Cnab.banco);

  private static final Map<Integer, Banco> codigoMap = newHashMap();

  static {
    for (Banco banco : Banco.values()) {
      int codigo = banco.getCodigo();
      codigoMap.put(codigo, banco);
    }
  }

  private final int codigo;

  private final Modelo modelo;

  private Banco(int codigo, Modelo modelo) {
    this.codigo = codigo;
    this.modelo = modelo;
  }

  public static Banco valueOf(int codigo) {
    Banco banco = OUTROS;

    if (codigoMap.containsKey(codigo)) {
      banco = codigoMap.get(codigo);
    }

    return banco;
  }

  public int getCodigo() {
    return codigo;
  }

  public Modelo getModelo() {
    return modelo;
  }

}