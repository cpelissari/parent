/*
 * ValorFinanceiroImpl.java criado em 21/03/2013
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.boleto;

import java.math.BigDecimal;

import br.com.objectos.comuns.matematica.financeira.AbstractMonetaryValue;
import br.com.objectos.comuns.matematica.financeira.ValorFinanceiro;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ValorFinanceiroImpl extends AbstractMonetaryValue {

  private static final long serialVersionUID = 1L;

  public ValorFinanceiroImpl(BigDecimal val) {
    super(val);
  }

  public ValorFinanceiroImpl(double val) {
    super(val);
  }

  @Override
  protected ValorFinanceiro newMonetaryValue(BigDecimal value) {
    return new ValorFinanceiroImpl(value);
  }

}