/*
 * MimeNoConv.java criado em 16/12/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import br.com.objectos.comuns.base.Construtor;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class MimeConvNone implements MimeConv {

  private final Attachment attach;

  public MimeConvNone(Attachment attach) {
    this.attach = attach;
  }

  @Override
  public List<AttachmentPage> extract() {
    try {
      AttachmentPage page = new PageBuilder().novaInstancia();
      Files.copy(attach.getFile(), page.getFile());
      return ImmutableList.of(page);
    } catch (IOException e) {
      throw new AttachmentException(
          "Could not save page of " + attach.getUuid(), e);
    }
  }

  private class PageBuilder implements AttachmentPage.Builder, Construtor<AttachmentPage> {

    @Override
    public AttachmentPage novaInstancia() {
      return new AttachmentPageImpl(this);
    }

    @Override
    public String getBaseDir() {
      return attach.getBaseDir();
    }

    @Override
    public UUID getUuid() {
      return attach.getUuid();
    }

    @Override
    public int getNumber() {
      return 1;
    }

  }

}