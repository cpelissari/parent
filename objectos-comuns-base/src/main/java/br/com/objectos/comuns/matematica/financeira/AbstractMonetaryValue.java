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
package br.com.objectos.comuns.matematica.financeira;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractMonetaryValue extends AbstractNumber implements ValorFinanceiro {

  private static final long serialVersionUID = 1L;

  protected AbstractMonetaryValue(BigDecimal val) {
    super(val, 15, 2);
  }

  protected AbstractMonetaryValue(double val) {
    this(new BigDecimal(val));
  }

  protected abstract ValorFinanceiro newMonetaryValue(BigDecimal value);

  @Override
  public ValorFinanceiro aplicarFator(ValorFinanceiro dividendo, ValorFinanceiro divisor) {
    BigDecimal _fator = obterFator(dividendo, divisor);

    BigDecimal _resultado = valor.multiply(_fator);

    return newMonetaryValue(_resultado);
  }

  @Override
  public ValorFinanceiro aplicarPercentual(Percentual percentual) {
    BigDecimal _fator = percentual.toBigDecimal();

    BigDecimal _resultado = valor.multiply(_fator);

    return newMonetaryValue(_resultado);
  }

  @Override
  public ValorFinanceiro divididoPor(BigDecimal divisor) {
    BigDecimal dividendo = this.toBigDecimal();

    BigDecimal quociente = dividir(dividendo, divisor);

    return newMonetaryValue(quociente);
  }

  @Override
  public ValorFinanceiro mais(ValorFinanceiro parcela) {
    BigDecimal soma = somarNumero(parcela);
    return newMonetaryValue(soma);
  }

  @Override
  public ValorFinanceiro menos(ValorFinanceiro subtraendo) {
    BigDecimal resto = subtrairNumero(subtraendo);
    return newMonetaryValue(resto);
  }

  @Override
  public ValorFinanceiro vezes(double fator) {
    return vezes(BigDecimal.valueOf(fator));
  }

  @Override
  public ValorFinanceiro vezes(BigDecimal fator) {
    BigDecimal _resultado = valor.multiply(fator);

    return newMonetaryValue(_resultado);
  }

  @Override
  public BigDecimal getValor() {
    return toBigDecimal();
  }

  @Override
  public int compareTo(ValorFinanceiro o) {
    return getValor().compareTo(o.getValor());
  }

  @Override
  public String toString() {
    NumberFormat nf = NumberFormat.getCurrencyInstance();
    return nf.format(doubleValue());
  }

}