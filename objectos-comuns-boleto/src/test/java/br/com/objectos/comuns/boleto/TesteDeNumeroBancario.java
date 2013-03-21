/*
 * TesteDeNumeroBancario.java criado em 21/03/2013
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
public class TesteDeNumeroBancario {

  public void numero_com_digito() {
    String numero = "123-4";

    NumeroBancario res = new NumeroBancario(numero);

    assertThat(res.longValue(), equalTo(123l));
    assertThat(res.getDigito(), equalTo('4'));
  }

  public void numero_sem_digito() {
    String numero = "789";

    NumeroBancario res = new NumeroBancario(numero);

    assertThat(res.longValue(), equalTo(789l));
    assertThat(res.getDigito(), equalTo((char) 0));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void nao_numero() {
    String numero = "abc";

    new NumeroBancario(numero);
  }

}