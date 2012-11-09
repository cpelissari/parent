/*
 * AjaxReplyJson.java criado em 26/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;


import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class AjaxReplyJson {

  private final Page page;

  private final MooReply reply;

  public AjaxReplyJson(Page page, MooReply reply) {
    Preconditions.checkArgument(reply.ajax());

    this.page = page;
    this.reply = reply;
  }

  public String getTitle() {
    return page.getTitle();
  }

  public String getBreadcrumbs() {
    return page.getBreadcrumbs();
  }

  public String getUrl() {
    return page.getUrl();
  }

  public String getHtml() {
    return reply.html();
  }

}