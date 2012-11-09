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
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Percentual extends AbstractNumber {

  private static final long serialVersionUID = 1L;

  public static final Percentual ZERO = new Percentual(0d);

  public static final Percentual _25_PORCENTO = new Percentual(0.25d);

  public static final Percentual _50_PORCENTO = new Percentual(0.5d);

  public static final Percentual _75_PORCENTO = new Percentual(0.75d);

  public static final Percentual _100_PORCENTO = new Percentual(1d);

  public static class PercentualDeNumber {

    private final Number number;

    public PercentualDeNumber(Number number) {
      this.number = Preconditions.checkNotNull(number);
    }

    public Percentual sobre(Number sobre) {
      double num = number.doubleValue();
      double den = sobre.doubleValue();
      return new Percentual(num / den);
    }

  }

  public static class PercentualDeNumero {

    private final Numero number;

    public PercentualDeNumero(Numero number) {
      this.number = Preconditions.checkNotNull(number);
    }

    public Percentual sobre(Numero sobre) {
      double num = number.doubleValue();
      double den = sobre.doubleValue();
      return new Percentual(num / den);
    }

  }

  public Percentual(double val) {
    super(val);
  }

  public Percentual(BigDecimal val) {
    super(val);
  }

  public static PercentualDeNumber de(Number number) {
    return new PercentualDeNumber(number);
  }
  public static PercentualDeNumero de(Numero numero) {
    return new PercentualDeNumero(numero);
  }

  @Override
  public Percentual aplicarPercentual(Percentual percentual) {
    BigDecimal _fator = percentual.toBigDecimal();

    BigDecimal _resultado = valor.multiply(_fator);

    return new Percentual(_resultado);
  }

  @Override
  public String toString() {
    return toStringWithDigits(2);
  }

  public String toStringWithDigits(int min) {
    Preconditions.checkArgument(min >= 0);
    DecimalFormat nf = (DecimalFormat) NumberFormat.getPercentInstance();
    nf.setMinimumFractionDigits(min);
    return nf.format(doubleValue());
  }

}