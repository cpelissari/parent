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
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;

import org.testng.annotations.Test;

/**
 * @author lenin.abe@objectos.com.br (Lenin Abe)
 */
@Test
public class TesteDeNumeroAbstrato {

  public void verifiqueConversoesParaTiposPrimitivos() {
    NumeroDeTeste positivo = obterNumero("5.12");

    assertEquals(positivo.doubleValue(), 5.12, 0.001);
    assertEquals(positivo.floatValue(), 5.12f, 0.001);
    assertEquals(positivo.intValue(), 5);
    assertEquals(positivo.longValue(), 5l);
  }

  public void verifiqueComparacoesLogicas() {
    NumeroDeTeste cinco = obterNumero("5");
    NumeroDeTeste dois = obterNumero("2");

    assertTrue(cinco.maiorQue(dois));
    assertTrue(cinco.maiorOuIgualA(dois));
    assertTrue(dois.menorQue(cinco));
    assertTrue(dois.menorOuIgualA(cinco));

    assertFalse(dois.maiorQue(cinco));
    assertFalse(dois.maiorOuIgualA(cinco));
    assertFalse(cinco.menorQue(dois));
    assertFalse(cinco.menorOuIgualA(dois));

    assertFalse(cinco.maiorQue(cinco));
    assertFalse(cinco.menorQue(cinco));

    assertTrue(cinco.maiorOuIgualA(cinco));
    assertTrue(cinco.menorOuIgualA(cinco));
  }

  public void verifiqueIsZero() {
    NumeroDeTeste zero = obterNumero("0.00");
    NumeroDeTeste um = obterNumero("1.01");

    assertTrue(zero.isZero());
    assertFalse(um.isZero());
  }

  public void verifiqueToString() {
    NumeroDeTeste numero = obterNumero("1.2");
    assertEquals(numero.toString(), "1.20");

    numero = obterNumero("0.0000000005");
    assertEquals(numero.toString(), "0.00");
  }

  @Test(expectedExceptions = { AssertionError.class })
  public void verifiqueToStringParaNumeroPequeno() {
    NumeroPequeno numero = obterNum("0.0000000001"); // 1.0000E-10

    assertEquals(numero.toString(), "0.00000000010000");
  }

  @Test(expectedExceptions = { AssertionError.class })
  public void verifiqueToStringParaNumeroPequenoComGrandePrecisao() {
    NumeroPequeno numero = obterNum("0.0000000123456789"); // 1.234568E-8

    assertEquals(numero.toString(), "0.0000000123456789");
  }

  public void verifiqueHashCode() {
    NumeroDeTeste valor = obterNumero("5");

    NumeroDeTeste idem = valor;

    Integer hash1 = valor.hashCode();
    Integer hash2 = idem.hashCode();

    assertEquals(hash1, hash2);
  }

  public void verifiqueContratoEqualsSemArrendodamento() {
    NumeroDeTeste dois = obterNumero("2");
    NumeroDeTeste oito = obterNumero("8");

    assertTrue(dois.equals(dois));
    assertTrue(oito.equals(oito));

    assertFalse(dois.equals(oito));
    assertFalse(oito.equals(dois));
    assertFalse(oito.equals(null));
    assertFalse(oito.equals(Integer.valueOf(8)));
  }

  public void verifiqueEqualsValoresArredondadosIguais() {
    NumeroDeTeste umVirgulaCentoENoventaENove = obterNumero("1.199");
    NumeroDeTeste umVirgulaDuzentosEUm = obterNumero("1.201");

    assertTrue(umVirgulaCentoENoventaENove.equals(umVirgulaDuzentosEUm));
  }

  public void verifiqueEqualsValoresArredondadosDiferentes() {
    NumeroDeTeste arredondamentoParInferior = obterNumero("1.201");
    NumeroDeTeste arredondamentoParSuparior = obterNumero("1.209");

    assertFalse(arredondamentoParInferior.equals(arredondamentoParSuparior));
  }

  public void verifiqueEqualsComNull() {
    NumeroDeTeste valorNaoNulo = obterNumero("3.75");

    assertFalse(valorNaoNulo.equals(null));
  }

  public void verifiqueQueTruncaImparPositivoParaCima() {
    NumeroDeTeste valor = obterNumero("6.335");
    String esperado = new String("6.34");

    assertEquals(valor.toString(), esperado);
  }

  public void verifiqueQueTruncaParPositivoParaBaixo() {
    NumeroDeTeste valor = obterNumero("6.345");
    String esperado = new String("6.34");

    assertEquals(valor.toString(), esperado);
  }

  public void verifiqueQueTruncaParNegativoParaBaixo() {
    NumeroDeTeste valor = obterNumero("-6.345");
    String esperado = new String("-6.34");

    assertEquals(valor.toString(), esperado);
  }

  public void verifiqueQueTruncaImparNegativoParaCima() {
    NumeroDeTeste valor = obterNumero("-6.335");
    String esperado = new String("-6.34");

    assertEquals(valor.toString(), esperado);
  }

  public void verifiqueSomar() {
    NumeroDeTeste doisEMeio = obterNumero("2.5");
    NumeroDeTeste umEMeio = obterNumero("1.5");

    BigDecimal quatro = new BigDecimal("4.0");

    assertEquals(doisEMeio.somarNumero(umEMeio), quatro);
  }

  public void verifiqueSubtrair() {
    NumeroDeTeste valor = new NumeroDeTeste("2.35");
    NumeroDeTeste subtraendo = obterNumero("1.2");

    BigDecimal resto = new BigDecimal("1.15");

    assertEquals(valor.subtrairNumero(subtraendo), resto);
  }

  @Test(expectedExceptions = { AssertionError.class })
  public void TesteDeEscalaMaior() {
    AbstractNumber numeroTeste = new NumeroDeTeste("3.1416");
    AbstractNumber numeroPequeno = new NumeroPequeno(numeroTeste.valor);

    assertEquals(numeroPequeno, numeroTeste);
  }

  @Test(expectedExceptions = { AssertionError.class })
  public void TesteDeEscalaMenor() {
    AbstractNumber numeroPequeno = new NumeroPequeno("7.6543");
    AbstractNumber numeroTeste = new NumeroDeTeste(numeroPequeno.valor);

    assertEquals(numeroTeste, numeroPequeno);
  }

  private NumeroDeTeste obterNumero(String valor) {
    return new NumeroDeTeste(valor);
  }

  private class NumeroDeTeste extends AbstractNumber {

    private static final long serialVersionUID = 1L;

    NumeroDeTeste(BigDecimal val) {
      super(val, 3, 2);
    }

    NumeroDeTeste(String val) {
      this(new BigDecimal(val));
    }

    NumeroDeTeste(BigDecimal val, int precision, int scale) {
      super(val, precision, scale);
    }

    @Override
    public Numero aplicarPercentual(Percentual percentual) {
      throw new UnsupportedOperationException();
    }

  }

  private NumeroPequeno obterNum(String string) {
    return new NumeroPequeno(string);
  }

  private class NumeroPequeno extends AbstractNumber {

    private static final long serialVersionUID = 1L;

    NumeroPequeno(BigDecimal val) {
      super(val, 16, 14);
    }

    NumeroPequeno(String val) {
      this(new BigDecimal(val));
    }

    @Override
    public Numero aplicarPercentual(Percentual percentual) {
      throw new UnsupportedOperationException();
    }

  }

}