/*
 * Html.java criado em 25/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import com.google.inject.ImplementedBy;
import com.google.sitebricks.client.Transport;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@ImplementedBy(HtmlTransport.class)
public abstract class Html implements Transport {

  @Override
  public String contentType() {
    return "text/html; charset=utf8";
  }

}
