/*
 * PageMeta.java criado em 28/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import java.util.List;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public interface MetaPageScript {

  void configure(MetaPageBuilder builder);

  List<MetaPageBuilder.Display> getElements();

}