/*
 * AnchorTest.java criado em 14/06/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.base.html;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class AnchorTest {

  public void anchor() {
    String res = new Anchor()
        .id("google")
        .href("http://www.google.com")
        .styleClass("btn btn-mini")
        .target("_blank")
        .html("Google!")
        .toString();

    assertThat(
        res,
        equalTo("<a id=\"google\" href=\"http://www.google.com\" class=\"btn btn-mini\" target=\"_blank\">Google!</a>"));
  }

}