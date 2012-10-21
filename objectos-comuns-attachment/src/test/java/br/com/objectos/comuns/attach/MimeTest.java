/*
 * TesteDeMime.java criado em 04/12/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import br.com.objectos.comuns.attach.AttachmentException;
import br.com.objectos.comuns.attach.Mime;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class MimeTest {

  public void teste_de_extrair() {
    String arquivo = "sdflkjlkjx.jpg";
    Mime res = Mime.valueOfContentType(arquivo);
    assertThat(res, equalTo(Mime.JPG));
  }

  @Test(expectedExceptions = AttachmentException.class)
  public void teste_de_formato_nao_valido() {
    String arquivo = "sdflkjlkjx.exe";
    Mime.valueOfContentType(arquivo);
  }

}