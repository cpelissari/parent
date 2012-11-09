/*
 * SpliceGuice.java criado em 26/09/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
class BricksGuice implements Bricks {

  private final BaseUrl baseUrl;

  private final PageGen pageGen;

  @Inject
  public BricksGuice(BaseUrl baseUrl, PageGen pageGen) {
    this.baseUrl = baseUrl;
    this.pageGen = pageGen;
  }

  @Override
  public String getBaseUrl() {
    return baseUrl.get();
  }

  @Override
  public Page pageOf(MetaPageScript script) {
    return pageGen.get(script);
  }

}