/*
 * TemplateRegistryGuice.java criado em 26/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import java.util.Set;

import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.SimpleTemplateRegistry;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRegistry;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.sitebricks.Template;
import com.google.sitebricks.TemplateLoader;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
class TemplateRegistryProvider implements Provider<TemplateRegistry> {

  private final Set<RegisteredTemplate> templates;

  private final TemplateLoader loader;

  @Inject
  public TemplateRegistryProvider(Set<RegisteredTemplate> templates, TemplateLoader loader) {
    this.templates = templates;
    this.loader = loader;
  }

  @Override
  public TemplateRegistry get() {
    TemplateRegistry registry = new SimpleTemplateRegistry();

    for (RegisteredTemplate template : templates) {
      String templateName = template.getName();
      Class<?> pageClass = template.getPageClass();
      Template loadedTemplate = loader.load(pageClass);

      CompiledTemplate compiledTemplate;
      compiledTemplate = TemplateCompiler.compileTemplate(loadedTemplate.getText());

      registry.addNamedTemplate(templateName, compiledTemplate);
    }

    return registry;
  }

}