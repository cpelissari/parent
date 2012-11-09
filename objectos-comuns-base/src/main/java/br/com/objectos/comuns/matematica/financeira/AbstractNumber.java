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
package br.com.objectos.comuns.matematica.financeira;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
abstract class AbstractNumber extends Number implements Numero {

  private static final long serialVersionUID = 1L;

  final BigDecimal valor;

  final BigDecimal valorComEscala;

  final int scale;

  AbstractNumber(double doubleValue) {
    valor = BigDecimal.valueOf(doubleValue);
    valorComEscala = valor;
    scale = 0;
  }

  AbstractNumber(BigDecimal val) {
    valor = val;
    valorComEscala = valor;
    scale = 0;
  }

  AbstractNumber(BigDecimal val, int precision, int scale) {
    Preconditions.checkNotNull(val, "Valor não pode ser null");
    this.valor = val;
    this.scale = scale;

    if (val.scale() != scale) {
      val = val.setScale(scale, RoundingMode.HALF_EVEN);
    }

    if (val.precision() > precision) {
      throw new NumberFormatException("Não será possível armazenar o valor '"
          + val + "' em BD. " + "Precisão máxima: " + precision
          + ", escala máxima: " + scale);
    }

    valorComEscala = val;
  }

  @Override
  public BigDecimal bigDecimalValue() {
    return valor;
  }

  @Override
  public double doubleValue() {
    return valor.doubleValue();
  }

  @Override
  public float floatValue() {
    return valor.floatValue();
  }

  @Override
  public int intValue() {
    return valor.intValue();
  }

  @Override
  public long longValue() {
    return valor.longValue();
  }

  @Override
  public boolean maiorQue(Numero outro) {
    BigDecimal valorDeComparacao = outro.toBigDecimal();
    return toBigDecimal().compareTo(valorDeComparacao) > 0;
  }

  @Override
  public boolean maiorOuIgualA(Numero outro) {
    BigDecimal valorDeComparacao = outro.toBigDecimal();
    return toBigDecimal().compareTo(valorDeComparacao) >= 0;
  }

  @Override
  public boolean menorQue(Numero outro) {
    BigDecimal valorDeComparacao = outro.toBigDecimal();
    return toBigDecimal().compareTo(valorDeComparacao) < 0;
  }

  @Override
  public boolean menorOuIgualA(Numero outro) {
    BigDecimal valorDeComparacao = outro.toBigDecimal();
    return toBigDecimal().compareTo(valorDeComparacao) <= 0;
  }

  @Override
  public boolean isZero() {
    return valorComEscala.compareTo(BigDecimal.ZERO) == 0;
  }

  @Override
  public BigDecimal toBigDecimal() {
    return valorComEscala;
  }

  BigDecimal obterFator(ValorFinanceiro dividendo, ValorFinanceiro divisor) {
    BigDecimal _dividendo = dividendo.bigDecimalValue();
    BigDecimal _divisor = divisor.bigDecimalValue();

    return dividir(_dividendo, _divisor);
  }

  BigDecimal dividir(Numero dividendo, Numero divisor) {
    BigDecimal _dividendo = dividendo.bigDecimalValue();
    BigDecimal _divisor = divisor.bigDecimalValue();

    return dividir(_dividendo, _divisor);
  }

  BigDecimal dividir(BigDecimal dividendo, BigDecimal divisor) {
    return dividendo.divide(divisor, 32, RoundingMode.HALF_EVEN);
  }

  BigDecimal multiplicarNumero(Numero fator) {
    return valor.multiply(fator.bigDecimalValue());
  }

  BigDecimal somarNumero(Numero parcela) {
    return valor.add(parcela.bigDecimalValue());
  }

  BigDecimal subtrairNumero(Numero subtraendo) {
    return valor.subtract(subtraendo.bigDecimalValue());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((valorComEscala == null) ? 0 : valorComEscala.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof AbstractNumber))
      return false;
    AbstractNumber other = (AbstractNumber) obj;
    if (valorComEscala == null) {
      if (other.valorComEscala != null)
        return false;
    } else if (!valorComEscala.equals(other.valorComEscala))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return valorComEscala.toString();
  }

}