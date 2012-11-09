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

import org.testng.annotations.Test;

/**
 * @author lenin.abe@objectos.com.br (Lenin Abe)
 */
@Test
public class TesteDeValorFinanceiro {

  private static final double PRECISAO = 0.001;

  public void verifiqueGetValor() {
    ValorFinanceiro valorFinanceiro = newValor("5");

    BigDecimal resposta = newBigDecimal("5.00");

    assertEquals(valorFinanceiro.getValor(), resposta);
  }

  public void verifiqueDivisaoPorBigdecimal() {
    ValorFinanceiro dividendo = newValor("5");
    BigDecimal divisor = newBigDecimal("2");

    ValorFinanceiro resultado = dividendo.divididoPor(divisor);

    assertValor(resultado, 2.5);
  }

  public void verifiqueQueSomaDeManeiraCorreta() {
    ValorFinanceiro soma = obterSoma("2.95", "1.05");

    assertEquals(soma, newValor("4.00"));
  }

  public void verifiqueArredondamentoDeEscalasDiferentes() {
    ValorFinanceiro soma = obterSoma("1230589.0455", "0.1235589055");

    ValorFinanceiro esperado = newValor("1230589.17");

    assertEquals(soma, esperado);
  }

  public void verifiqueMultiplicacao() {
    ValorFinanceiro cinco = newValor("5");
    double dois = 2d;

    ValorFinanceiro dez = newValor("10");

    ValorFinanceiro resultado = cinco.vezes(dois);

    assertEquals(resultado, dez);
  }

  public void verifiqueSoma() {
    ValorFinanceiro soma = adicao("-10.25", "30");

    ValorFinanceiro valorEsperado = newValor("19.75");

    assertEquals(soma, valorEsperado);
  }

  @Test(expectedExceptions = { NumberFormatException.class })
  public void verifiqueSomaDeValoresAltos() {
    String numeroComMuitosZeros = "7000000000000.00";

    ValorFinanceiro soma = adicao(numeroComMuitosZeros, numeroComMuitosZeros);

    assertEquals(soma.toString(), "14000000000000.00");
  }

  public void verifiqueSubtracao() {
    ValorFinanceiro valor = subtracao("25.25", "15.5");

    ValorFinanceiro valorEsperado = newValor("9.75");

    assertEquals(valor, valorEsperado);
  }

  public void verifiqueAplicarFator() {
    ValorFinanceiro valor = aplicarFator("851.75", "4", "3");

    ValorFinanceiro valorEsperado = newValor("1135.67");

    assertEquals(valor, valorEsperado);
  }

  private ValorFinanceiro obterSoma(String valor, String parcela) {
    ValorFinanceiro valorInicial = newValor(valor);

    ValorFinanceiro outroValor = newValor(parcela);

    return valorInicial.mais(outroValor);
  }

  private BigDecimal newBigDecimal(String valor) {
    return new BigDecimal(valor);
  }

  private ValorFinanceiro newValor(String valor) {
    BigDecimal bigDecimal = new BigDecimal(valor);
    return new FakeMonetaryValue(bigDecimal);
  }

  private void assertValor(ValorFinanceiro valor, double esperado) {
    assertEquals(valor.doubleValue(), esperado, PRECISAO);
  }

  private ValorFinanceiro adicao(String valor, String incremento) {
    ValorFinanceiro val = newValor(valor);

    ValorFinanceiro incr = newValor(incremento);

    return val.mais(incr);
  }

  private ValorFinanceiro subtracao(String valor, String subtraendo) {
    ValorFinanceiro val = newValor(valor);

    ValorFinanceiro sub = newValor(subtraendo);

    return val.menos(sub);
  }

  private ValorFinanceiro aplicarFator(String valor, String multiplicador,
      String divisor) {
    ValorFinanceiro val = newValor(valor);

    ValorFinanceiro mult = newValor(multiplicador);

    ValorFinanceiro div = newValor(divisor);

    return val.aplicarFator(mult, div);
  }

}
