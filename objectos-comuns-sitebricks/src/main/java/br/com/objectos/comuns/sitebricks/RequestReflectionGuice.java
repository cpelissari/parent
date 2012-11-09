/*
 * RequestReflectionGuice.java criado em 26/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.sitebricks.headless.Request;

/**
 * Just to make it easier to test ReplyGen.
 * 
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
class RequestReflectionGuice implements RequestReflection {

  private final Provider<Request> requestProvider;

  @Inject
  public RequestReflectionGuice(Provider<Request> requestProvider) {
    this.requestProvider = requestProvider;
  }

  @Override
  public boolean isMootoolsAjax() {
    // http://davidwalsh.name/mootools-history
    Request request = requestProvider.get();
    String header = request.header("X-Requested-With");

    return header != null && header.toLowerCase().equals("xmlhttprequest");
  }

}