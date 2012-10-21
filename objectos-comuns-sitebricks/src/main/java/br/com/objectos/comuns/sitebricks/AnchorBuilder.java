/*
 * AnchorBuilder.java criado em 17/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class AnchorBuilder implements Supplier<Anchor> {

  private String url;
  private String text;

  @Override
  public Anchor get() {
    return new AnchorImpl();
  }

  public AnchorBuilder url(String url) {
    this.url = url;
    return this;
  }

  public AnchorBuilder text(String text) {
    this.text = text;
    return this;
  }

  private class AnchorImpl implements Anchor {

    public AnchorImpl() {
      Preconditions.checkNotNull(url);
      Preconditions.checkNotNull(text);
    }

    @Override
    public String getUrl() {
      return url;
    }

    @Override
    public String getText() {
      return text;
    }

  }

}