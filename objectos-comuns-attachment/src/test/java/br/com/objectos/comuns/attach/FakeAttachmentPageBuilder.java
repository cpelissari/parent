/*
 * FakeAttachmentPageBuilder.java criado em 04/10/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class FakeAttachmentPageBuilder implements AttachmentPage.Builder {

  @Override
  public Attachment getAttachment() {
    return null;
  }

  @Override
  public int getNumber() {
    return 0;
  }

  @Override
  public boolean isOriginal() {
    return false;
  }

}