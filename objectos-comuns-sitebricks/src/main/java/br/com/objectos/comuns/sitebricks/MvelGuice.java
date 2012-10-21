/*
 * MvelRunnerGuice.java criado em 26/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRegistry;
import org.mvel2.templates.TemplateRuntime;

import com.google.inject.Inject;
import com.google.sitebricks.Template;
import com.google.sitebricks.TemplateLoader;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class MvelGuice implements Mvel {

  private final TemplateLoader templateLoader;

  private final TemplateRegistry templateRegistry;

  @Inject
  public MvelGuice(TemplateLoader templateLoader, TemplateRegistry templateRegistry) {
    this.templateLoader = templateLoader;
    this.templateRegistry = templateRegistry;
  }

  @Override
  public String render(Object ctx) {
    Class<? extends Object> pageClass = ctx.getClass();
    Template template = templateLoader.load(pageClass);

    CompiledTemplate compiledTemplate = TemplateCompiler.compileTemplate(template.getText());

    Object contents = TemplateRuntime.execute(compiledTemplate, ctx, templateRegistry);

    return contents.toString();
  }

}