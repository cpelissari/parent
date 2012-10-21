/*
 * MimeNoConv.java criado em 16/12/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import java.util.List;

import br.com.objectos.comuns.base.Construtor;

import com.google.common.collect.ImmutableList;

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
    AttachmentPage page = new PageBuilder().novaInstancia();

    return ImmutableList.of(page);
  }

  private class PageBuilder implements AttachmentPage.Builder, Construtor<AttachmentPage> {

    @Override
    public AttachmentPage novaInstancia() {
      return new AttachmentPageImpl(this);
    }

    @Override
    public Attachment getAttachment() {
      return attach;
    }

    @Override
    public int getNumber() {
      return 1;
    }

    @Override
    public boolean isOriginal() {
      return true;
    }

  }

}