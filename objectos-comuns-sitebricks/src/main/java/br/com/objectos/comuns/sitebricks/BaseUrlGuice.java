/*
 * UrlBaseGuice.java criado em 17/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
class BaseUrlGuice implements BaseUrl {

  private final Provider<HttpServletRequest> requests;

  @Inject
  BaseUrlGuice(Provider<HttpServletRequest> requests) {
    this.requests = requests;
  }

  @Override
  public String get() {
    HttpServletRequest req = requests.get();
    String scheme = req.getScheme();
    String serverName = req.getServerName();
    int port = req.getServerPort();
    String contextPath = req.getContextPath();

    if (port == 80) {
      return String.format("%s://%s%s", scheme, serverName, contextPath);
    } else {
      return String.format("%s://%s:%d%s", scheme, serverName, port, contextPath);
    }
  }

  @Override
  public String toString() {
    // I know devs will forget to call the get() method... sigh...
    return get();
  }

}