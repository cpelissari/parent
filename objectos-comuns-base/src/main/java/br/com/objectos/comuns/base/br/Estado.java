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
package br.com.objectos.comuns.base.br;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public enum Estado {

  AC("Acre"),
  AL("Alagoas"),
  AP("Amapá"),
  AM("Amazonas"),
  BA("Bahia"),
  CE("Ceará"),
  ES("Espiríto Santo"),
  GO("Goiás"),
  MA("Maranhão"),
  MT("Mato Grosso"),
  MS("Mato Grosso do Sul"),
  MG("Minas Gerais"),
  PA("Pará"),
  PB("Paraíba"),
  PR("Paraná"),
  PE("Pernambuco"),
  PI("Piauí"),
  RJ("Rio de Janeiro"),
  RN("Rio Grande do Norte"),
  RS("Rio Grande do Sul"),
  RO("Rondônia"),
  RR("Roraima"),
  SC("Santa Catarina"),
  SP("São Paulo"),
  SE("Sergipe"),
  TO("Tocantins"),
  DF("Distrito Federal");

  private String name;

  private Estado(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  private static List<String> symbols;

  public static List<String> getSymbols() {
    if (symbols == null) {
      symbols = Lists.newArrayList();
      for (Estado state : values()) {
        symbols.add(state.name());
      }
    }
    return symbols;
  }
}
