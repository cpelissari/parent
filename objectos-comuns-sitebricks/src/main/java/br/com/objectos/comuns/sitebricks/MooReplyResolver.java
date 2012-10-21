/*
 * MooReplyResolver.java criado em 26/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;


import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class MooReplyResolver {

  private final Page page;
  private final MooReply reply;

  public MooReplyResolver(Page page, MooReply reply) {
    this.page = page;
    this.reply = reply;
  }

  public Reply<?> resolve() {
    if (reply.ajax()) {
      AjaxReplyJson json = new AjaxReplyJson(page, reply);
      return Reply.with(json).as(Json.class);
    } else {
      String html = reply.html();
      return Reply.with(html).as(Html.class);
    }
  }

}