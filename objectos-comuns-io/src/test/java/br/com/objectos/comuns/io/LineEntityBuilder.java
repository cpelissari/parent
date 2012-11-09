/*
 * LineEntityBuilder.java criado em 09/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.io;

import org.joda.time.LocalDate;

import br.com.objectos.comuns.base.br.Estado;
import br.com.objectos.comuns.io.Line;
import br.com.objectos.comuns.util.Booleano;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class LineEntityBuilder implements Entity.Builder {

  private final Line line;

  public LineEntityBuilder(Line line) {
    this.line = line;
  }

  @Override
  public Entity build() {
    return new EntityPojo(this);
  }

  @Override
  public Boolean booleanValue() {
    return line.column(0).get(Boolean.class);
  }

  @Override
  public Booleano booleanoValue() {
    return line.column(1).get(Booleano.class);
  }

  @Override
  public Double doubleValue() {
    return line.column(2).get(Double.class);
  }

  @Override
  public Estado estadoValue() {
    return line.column(3).get(Estado.class);
  }

  @Override
  public Integer integerValue() {
    return line.column(4).get(Integer.class);
  }

  @Override
  public LocalDate localDate() {
    return line.column(5).get(LocalDate.class);
  }

  @Override
  public Long longValue() {
    return line.column(6).get(Long.class);
  }

  @Override
  public String stringValue() {
    return line.column(7).get(String.class);
  }

}