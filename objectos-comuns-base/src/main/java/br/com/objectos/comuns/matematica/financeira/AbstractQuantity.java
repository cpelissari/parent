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
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractQuantity extends AbstractNumber implements Quantidade {

  private static final long serialVersionUID = 1L;

  public AbstractQuantity(BigDecimal val) {
    super(val, 22, 9);
  }

  public AbstractQuantity(String val) {
    this(new BigDecimal(val));
  }

  public AbstractQuantity(double val) {
    this(new BigDecimal(val));
  }

  protected abstract ValorFinanceiro newMonetaryValue(BigDecimal value);

  protected abstract Quantidade newQuantity(BigDecimal value);

  @Override
  public Quantidade aplicarPercentual(Percentual percentual) {
    BigDecimal _fator = percentual.toBigDecimal();

    BigDecimal _resultado = valor.multiply(_fator);

    return newQuantity(_resultado);
  }

  @Override
  public Quantidade aplicarFator(ValorFinanceiro dividendo,
      ValorFinanceiro divisor) {
    BigDecimal _fator = obterFator(dividendo, divisor);

    BigDecimal _resultado = valor.multiply(_fator);

    return newQuantity(_resultado);
  }

  @Override
  public Participacao calcularParticipacao(Quantidade quantidadeTotal) {
    BigDecimal quociente = dividir(this, quantidadeTotal);
    return new ParticipacaoImpl(quociente);
  }

  @Override
  public Quantidade mais(Quantidade parcela) {
    BigDecimal soma = somarNumero(parcela);
    return newQuantity(soma);
  }

  @Override
  public ValorFinanceiro vezes(ValorFinanceiro pu) {
    BigDecimal produto = multiplicarNumero(pu);
    return newMonetaryValue(produto);
  }

  @Override
  public BigDecimal getQuantidade() {
    return toBigDecimal();
  }

  @Override
  public int compareTo(Quantidade o) {
    return getQuantidade().compareTo(o.getQuantidade());
  }

  @Override
  public String toString() {
    DecimalFormat nf = (DecimalFormat) NumberFormat.getNumberInstance();
    nf.setMinimumFractionDigits(4);
    return nf.format(doubleValue());
  }

}