/*
 * FakeAttachmentPageBuilder.java criado em 04/10/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import java.util.UUID;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class FakeAttachmentPageBuilder implements AttachmentPage.Builder {

  @Override
  public String getBaseDir() {
    return null;
  }

  @Override
  public UUID getUuid() {
    return null;
  }

  @Override
  public int getNumber() {
    return 0;
  }

}