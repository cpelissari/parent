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
package br.com.objectos.comuns.boleto;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.bancos.Bradesco;
import br.com.caelum.stella.boleto.bancos.Caixa;
import br.com.caelum.stella.boleto.bancos.Itau;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
public enum BoletoBanco {
  BANCO_DO_BRASIL(1) {
    @Override
    Banco getbanco() {
      return new BancoDoBrasil();
    }
  },
  BRADESCO(237) {
    @Override
    Banco getbanco() {
      return new Bradesco();
    }
  },
  CAIXA(104) {
    @Override
    Banco getbanco() {
      return new Caixa();
    }
  },
  ITAU(34) {
    @Override
    Banco getbanco() {
      return new Itau();
    }
  };

  private static final Map<Integer, BoletoBanco> codigoMap = newHashMap();

  static {
    for (BoletoBanco boletoBanco : BoletoBanco.values()) {
      int codigo = boletoBanco.getCodigo();
      codigoMap.put(codigo, boletoBanco);
    }
  }

  private final int codigo;

  private BoletoBanco(int codigo) {
    this.codigo = codigo;
  }

  public static BoletoBanco valueOf(int codigo) {
    BoletoBanco boletoBanco = BoletoBanco.BANCO_DO_BRASIL;

    if (codigoMap.containsKey(codigo)) {
      boletoBanco = codigoMap.get(codigo);
    }

    return boletoBanco;
  }

  public int getCodigo() {
    return codigo;
  }

  abstract Banco getbanco();

}