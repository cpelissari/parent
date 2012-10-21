/*
 * MooReplyGenGuice.java criado em 25/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class MooReplyGenGuice implements MooReplyGen {

  private final Mvel mvel;

  private final RequestReflection requestReflection;

  @Inject
  public MooReplyGenGuice(Mvel mvel, RequestReflection requestReflection) {
    this.mvel = mvel;
    this.requestReflection = requestReflection;
  }

  @Override
  public MooReply get(Object ctx) {
    String html = mvel.render(ctx);

    boolean ajax = requestReflection.isMootoolsAjax();
    if (ajax) {

      Document doc = Jsoup.parse(html);
      Element bd = doc.getElementById("bd");
      html = bd.html();

    }

    return new MooReplyImpl(ajax, html);
  }

  private static class MooReplyImpl implements MooReply {

    private final boolean ajax;
    private final String html;

    public MooReplyImpl(boolean ajax, String html) {
      this.ajax = ajax;
      this.html = html;
    }

    @Override
    public boolean ajax() {
      return ajax;
    }

    @Override
    public String html() {
      return html;
    }

  }

}