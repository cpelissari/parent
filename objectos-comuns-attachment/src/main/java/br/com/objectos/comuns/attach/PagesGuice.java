/*
 * PaginaDeAnexoGenGuice.java criado em 16/12/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import java.util.List;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class PagesGuice implements Pages {

  @Override
  public Attachment addPagesTo(Attachment attach) {
    if (attach.isValid()) {
      List<AttachmentPage> pages = getPages(attach);
      attach = new FinalAttachment(attach, pages);
    }

    return attach;
  }

  private List<AttachmentPage> getPages(Attachment attach) {
    Mime mime = attach.getMime();

    MimeConv conv;
    switch (mime) {
    default:
    case GIF:
    case JPEG:
    case JPG:
    case PNG:
      conv = new MimeConvNone(attach);
      break;

    case PDF:
      conv = new MimeConvPdf(attach);
      break;
    }

    return conv.extract();
  }

}