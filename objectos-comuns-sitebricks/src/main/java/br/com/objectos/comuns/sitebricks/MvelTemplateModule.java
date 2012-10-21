/*
 * MvelTemplateModule.java criado em 26/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import org.mvel2.templates.TemplateRegistry;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class MvelTemplateModule extends AbstractModule {

  private Multibinder<RegisteredTemplate> multibinder;

  @Override
  protected final void configure() {
    multibinder = Multibinder.newSetBinder(binder(), RegisteredTemplate.class);

    configureTemplates();

    bind(TemplateRegistry.class).toProvider(TemplateRegistryProvider.class);
  }

  protected abstract void configureTemplates();

  protected IncludePage include(Class<?> pageClass) {
    return new IncludePage(pageClass);
  }

  protected class IncludePage {
    private final Class<?> pageClass;

    public IncludePage(Class<?> pageClass) {
      this.pageClass = pageClass;
    }

    public void as(String name) {
      RegisteredTemplate instance = new RegisteredTemplateImpl(name, pageClass);
      multibinder.addBinding().toInstance(instance);
    }
  }

  private static class RegisteredTemplateImpl implements RegisteredTemplate {

    private final String name;
    private final Class<?> pageClass;

    public RegisteredTemplateImpl(String name, Class<?> pageClass) {
      this.name = name;
      this.pageClass = pageClass;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public Class<?> getPageClass() {
      return pageClass;
    }
  }

}