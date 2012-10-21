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

import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.testng.annotations.Test;

/**
 * @author lenin.abe@objectos.com.br (Lenin Abe)
 */
@Test
public class TesteDeQuantidade {

  public void verifiqueCalcularParticipacao() {
    BigDecimal quociente = obterQuociente();

    Participacao participacao = new ParticipacaoImpl(quociente);

    assertEquals(participacao.doubleValue(), 0.1875, 0.001);
  }

  public void verifiqueAplicarFator() {
    Quantidade quantidade = obterQuantidade("2345.67");

    ValorFinanceiro dividendo = obterValorFinanceiro("7");

    ValorFinanceiro divisor = obterValorFinanceiro("9");

    Quantidade novaQuantidade = quantidade.aplicarFator(dividendo, divisor);

    assertEquals(novaQuantidade, newQuantity(1824.41));
  }

  public void verifiqueFatorArredondamentosSucessivos() {
    Quantidade quantidade = obterQuantidade(".00015");

    ValorFinanceiro dividendo = obterValorFinanceiro("73098700");

    ValorFinanceiro divisor = obterValorFinanceiro("0.0068017");

    Quantidade novaQuantidade = quantidade.aplicarFator(dividendo, divisor);

    Quantidade mesmaQuantidade = novaQuantidade
        .aplicarFator(divisor, dividendo);

    assertEquals(mesmaQuantidade, newQuantity(0.00015));
  }

  public void verifiqueMultiplicacao() {
    Quantidade dois = obterQuantidade("2");

    ValorFinanceiro quatro = obterValorFinanceiro("4");

    ValorFinanceiro oito = dois.vezes(quatro);

    verifique(oito, 8);
  }

  public void verifiqueMultiplicacaoPorZero() {
    Quantidade zero = obterQuantidade("0");

    ValorFinanceiro quatro = obterValorFinanceiro("4");

    ValorFinanceiro resultado = zero.vezes(quatro);

    verifique(resultado, 0d);
  }

  private Quantidade obterQuantidade(String valor) {
    BigDecimal val = new BigDecimal(valor);

    return newQuantity(val);
  }

  private Quantidade newQuantity(BigDecimal val) {
    return new FakeQuantity(val);
  }
  private Quantidade newQuantity(double val) {
    return new FakeQuantity(val);
  }

  private BigDecimal obterQuociente() {
    BigDecimal quociente = bd("150").divide(bd("800"), 16,
        RoundingMode.HALF_EVEN);

    return quociente;
  }

  private BigDecimal bd(String numero) {
    return new BigDecimal(numero);
  }

  private ValorFinanceiro obterValorFinanceiro(String valor) {
    BigDecimal val = new BigDecimal(valor);

    return new FakeMonetaryValue(val);
  }

  private void verifique(Numero numero, double esperado) {
    assertEquals(numero.doubleValue(), esperado, 0.01);
  }

}
