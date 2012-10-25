/*
 * Anchor.java criado em 17/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@JsonSerialize(using = AnchorSerializer.class)
public interface Anchor {

  String getUrl();

  String getText();

  boolean isFirst();
  boolean isLast();

}