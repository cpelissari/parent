/*
 * TesteDeNossoNumero.java criado em 21/03/2013
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.boleto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TesteDeNossoNumero {

  public void numero() {
    String numero = "1234";

    NossoNumero res = new NossoNumero(numero);

    assertThat(res.longValue(), equalTo(123l));
    assertThat(res.getDigito(), equalTo('4'));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void nao_numero() {
    String numero = "abc";

    new NossoNumero(numero);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void nao_numero_hifen() {
    String numero = "123-4";

    new NossoNumero(numero);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void nao_numero_vazio() {
    String numero = "";

    new NossoNumero(numero);
  }

}