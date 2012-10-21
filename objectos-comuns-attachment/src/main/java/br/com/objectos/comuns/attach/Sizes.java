/*
 * Tamanho.java criado em 04/12/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import static br.com.objectos.comuns.attach.Size.original;
import static br.com.objectos.comuns.attach.Size.widthOf;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public enum Sizes implements SizeEnum {

  ORIGINAL(original()),

  S(widthOf(320)),

  M(widthOf(740)),

  L(widthOf(920));

  private final Size size;

  private Sizes(Size size) {
    this.size = size;
  }

  @Override
  public Size asSize() {
    return size;
  }

}