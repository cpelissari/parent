/*
 * Splice.java criado em 26/09/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import com.google.inject.ImplementedBy;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@ImplementedBy(BricksGuice.class)
public interface Bricks {

  String getBaseUrl();

  Page pageOf(MetaPageScript script);

}