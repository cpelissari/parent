/*
 * PrettyDateTest.java criado em 08/02/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class PrettyDateTest {

  private static final DateTime NOW = new DateTime();

  public void seconds() {
    DateTime dateTime = NOW.minusSeconds(2);
    String res = PrettyDate.print(dateTime);
    assertThat(res, endsWith("seg. atrás"));
  }

  public void minutes() {
    DateTime dateTime = NOW.minusMinutes(2);
    String res = PrettyDate.print(dateTime);
    assertThat(res, endsWith("min. atrás"));
  }

  public void hours() {
    DateTime dateTime = NOW.minusHours(2);
    String res = PrettyDate.print(dateTime);
    assertThat(res, endsWith("hora(s) atrás"));
  }

  public void days() {
    DateTime dateTime = NOW.minusDays(2);
    String res = PrettyDate.print(dateTime);
    assertThat(res, endsWith("dia(s) atrás"));
  }

  public void months() {
    DateTime dateTime = NOW.minusDays(40);
    String res = PrettyDate.print(dateTime);
    assertThat(res, endsWith("mês(es) atrás"));
  }

}