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
package br.com.objectos.comuns.base.br;

import br.com.objectos.comuns.matematica.Decomposicao;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Cpf extends CadastroRFB {

  private static final int[] FATORES = {
    2,
    3,
    4,
    5,
    6,
    7,
    8,
    9,
    10,
    11 };

  private final long value;

  private final int inscricao;

  private final int digito;

  private final boolean valido;

  private Cpf(long value) {
    this.value = value;
    this.inscricao = inscricaoDe(value);
    this.digito = digitoDe(value);
    this.valido = validoDe(value);
  }

  public static Cpf parseCPF(String val) {
    try {
      String somenteNumeros = val.replaceAll("[^\\d]", "");
      long longValue = Long.parseLong(somenteNumeros);
      Cpf cpf = Cpf.valueOf(longValue);

      if (cpf.isValido()) {
        return cpf;
      } else {
        throw new ExcecaoDeCpfInvalido(cpf);
      }
    } catch (NumberFormatException e) {
      throw new ExcecaoDeCpfInvalido(val);
    }
  }

  public static Cpf valueOf(long val) {
    return new Cpf(val);
  }

  @Override
  public final TipoDeCadastroRFB getTipo() {
    return TipoDeCadastroRFB.CPF;
  }

  @Override
  public long longValue() {
    return value;
  }

  @Override
  public int getInscricao() {
    return inscricao;
  }

  @Override
  public int getDigito() {
    return digito;
  }

  @Override
  public boolean isValido() {
    return valido;
  }

  @Override
  public String toString() {
    return String.format("%1$,d-%2$02d", inscricao, digito);
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (value ^ (value >>> 32));
    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Cpf)) {
      return false;
    }
    Cpf other = (Cpf) obj;
    if (value != other.value) {
      return false;
    }
    return true;
  }

  private int inscricaoDe(long value) {
    return (int) (value / 100l);
  }

  private int digitoDe(long value) {
    return (int) (value % 100l);
  }

  private boolean validoDe(long value) {
    int[] cpf = Decomposicao.base10Inversa(value);

    int digito1 = calculaDigito(cpf, 2);
    int digito0 = calculaDigito(cpf, 1);

    return cpf[0] == digito0 && cpf[1] == digito1;
  }

  private static int calculaDigito(int[] cpf, int offset) {
    int soma = 0;

    for (int i = 0; i < FATORES.length; i++) {
      soma += cpf[i + offset] * FATORES[i];
    }

    int resto = soma % 11;

    return resto < 2 ? 0 : 11 - resto;
  }

}