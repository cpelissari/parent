/*
 * MarkdowTest.java criado em 14/06/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.base.html;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import br.com.objectos.comuns.base.html.Markdown;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class MarkdowTest {

  public void link() {
    String res = new Markdown()
        .link("Google").toUrl("http://www.google.com")
        .toString();

    assertThat(res, equalTo("[Google](http://www.google.com)"));
  }

  public void link_with_template() {
    String res = new Markdown()
        .link("My %s", "link").toUrl("/server/%s", "my_page")
        .toString();

    assertThat(res, equalTo("[My link](/server/my_page)"));
  }

  public void paragraph() {
    String res = new Markdown()
        .text("a simple text").text(" ").text("plus %s", "more")
        .toString();

    assertThat(res, equalTo("a simple text plus more"));
  }

  public void paragraph_with_objects() {
    String res = new Markdown()
        .text("numbers: ")
        .text(Integer.valueOf(1))
        .text(" ")
        .text(Long.valueOf(2))
        .toString();

    assertThat(res, equalTo("numbers: 1 2"));
  }

  public void line_break() {
    String res = new Markdown()
        .text("a line")
        .br()
        .text("another line")
        .toString();

    assertThat(res, equalTo("a line\n\nanother line"));
  }

}