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

import java.io.Serializable;
import java.util.Locale;

import br.com.objectos.comuns.matematica.Decomposicao;

/**
 * @author aline.heredia@objectos.com.br (Aline Heredia)
 */
public class Cnpj extends CadastroRFB implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final int[] FATORES = {
    2,
    3,
    4,
    5,
    6,
    7,
    8,
    9,
    2,
    3,
    4,
    5,
    6 };

  private final long longValue;

  private final int inscricao;

  private final int filiais;

  private final int digito;

  private final String toString;

  private Cnpj(long longValue) {
    this.longValue = longValue;
    long temp = longValue;

    this.digito = (int) (temp % 100l);
    temp = temp / 100l;

    this.filiais = (int) (temp % 10000);
    temp = temp / 10000l;

    this.inscricao = (int) temp;

    Locale l = new Locale("pt");
    this.toString = String.format(l, "%,010d/%04d-%02d", inscricao, filiais, digito);
  }

  @Override
  public final TipoDeCadastroRFB getTipo() {
    return TipoDeCadastroRFB.CNPJ;
  }

  @Override
  public long longValue() {
    return longValue;
  }

  @Override
  public int getInscricao() {
    return inscricao;
  }

  public int getFiliais() {
    return filiais;
  }

  @Override
  public int getDigito() {
    return digito;
  }

  @Override
  public boolean isValido() {
    int[] cnpj = Decomposicao.base10Inversa(longValue);

    int digito1 = calculaDigito(cnpj, 2);
    int digito0 = calculaDigito(cnpj, 1);

    return cnpj[0] == digito0 && cnpj[1] == digito1;
  }

  @Override
  public String toString() {
    return toString;
  }

  public static Cnpj valueOf(long valor) {
    Cnpj cnpj = new Cnpj(valor);

    if (!cnpj.isValido()) {
      String format = "O CNPJ representado por %s é inválido";
      String msg = String.format(format, Long.valueOf(valor));
      throw new ExcecaoDeCnpjInvalido(msg);
    }

    return cnpj;
  }

  public static Cnpj valueOf(int inscricao, int filiais) {
    long cnpjSemDigito = inscricao * 10000l;
    cnpjSemDigito += filiais;

    int[] array = Decomposicao.base10Inversa(cnpjSemDigito);

    int digito1 = calculaDigito(array, 0);

    int[] array1 = new int[array.length + 1];
    array1[0] = digito1;
    System.arraycopy(array, 0, array1, 1, array.length);

    int digito0 = calculaDigito(array1, 0);

    long cnpj = inscricao * 10000l;

    cnpj += filiais;
    cnpj *= 10;
    cnpj += digito1;
    cnpj *= 10;
    cnpj += digito0;

    return valueOf(cnpj);
  }

  public static Cnpj valueOf(String valor) {
    try {

      String somenteNumeros = valor.replaceAll("[^\\d]", "");
      long longValue = Long.parseLong(somenteNumeros);
      return valueOf(longValue);

    } catch (NumberFormatException e) {

      String msg = String
          .format("O CNPJ representado por %s é inválido", valor);
      throw new ExcecaoDeCnpjInvalido(msg);

    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (longValue ^ (longValue >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Cnpj other = (Cnpj) obj;
    if (longValue != other.longValue)
      return false;
    return true;
  }

  private static int calculaDigito(int[] cnpj, int offset) {
    int soma = 0;

    for (int i = 0; i < FATORES.length; i++) {
      soma += cnpj[i + offset] * FATORES[i];
    }

    int resto = soma % 11;

    return resto < 2 ? 0 : 11 - resto;
  }

}