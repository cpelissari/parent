/*
 * AbstractPageMeta.java criado em 28/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import java.util.List;

import br.com.objectos.comuns.sitebricks.MetaPageBuilder.Display;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractPageMeta implements MetaPageScript {

  private MetaPageBuilder binder;

  @Override
  public final synchronized void configure(MetaPageBuilder builder) {
    // cool!
    // http://code.google.com/p/google-guice/source/browse/tags/2.0/src/com/google/inject/AbstractModule.java#54
    Preconditions.checkState(this.binder == null, "Re-entry is not allowed.");

    this.binder = Preconditions.checkNotNull(builder, "meta");
    try {
      pageMetaFor();
    } finally {
      this.binder = null;
    }
  }

  @Override
  public List<Display> getElements() {
    return binder.getElements();
  }

  protected abstract void pageMetaFor();

  protected Display display(String title) {
    return binder.display(title);
  }

  protected void install(MetaPageScript script) {
    binder.install(script);
  }

  protected MetaPageBuilder binder() {
    return binder;
  }

}