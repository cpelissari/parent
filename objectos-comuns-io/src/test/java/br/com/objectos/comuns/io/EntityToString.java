/*
 * EntityToString.java criado em 09/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.io;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.google.common.base.Function;
import com.google.common.base.Objects;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class EntityToString implements Function<Entity, String> {

  private static final NumberFormat fmt = DecimalFormat.getNumberInstance();

  @Override
  public String apply(Entity input) {
    return Objects.toStringHelper(input)
        .addValue(input.booleanValue())
        .addValue(input.booleanoValue())
        .addValue(fmt.format(input.doubleValue()))
        .addValue(input.estadoValue())
        .addValue(input.integerValue())
        .addValue(input.localDate())
        .addValue(input.longValue())
        .addValue(input.stringValue())
        .toString();
  }

}