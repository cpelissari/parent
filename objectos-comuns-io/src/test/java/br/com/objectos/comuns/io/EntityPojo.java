/*
 * EntityPojo.java criado em 09/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.io;

import org.joda.time.LocalDate;

import br.com.objectos.comuns.base.br.Estado;
import br.com.objectos.comuns.util.Booleano;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class EntityPojo implements Entity {

  private final Boolean booleanValue;
  private final Booleano booleanoValue;
  private final Double doubleValue;
  private final Estado estadoValue;
  private final Integer integerValue;
  private final LocalDate localDate;
  private final Long longValue;
  private final String stringValue;

  public EntityPojo(Builder builder) {
    booleanValue = builder.booleanValue();
    booleanoValue = builder.booleanoValue();
    doubleValue = builder.doubleValue();
    estadoValue = builder.estadoValue();
    integerValue = builder.integerValue();
    localDate = builder.localDate();
    longValue = builder.longValue();
    stringValue = builder.stringValue();
  }

  @Override
  public Boolean booleanValue() {
    return booleanValue;
  }

  @Override
  public Booleano booleanoValue() {
    return booleanoValue;
  }

  @Override
  public Double doubleValue() {
    return doubleValue;
  }

  @Override
  public Estado estadoValue() {
    return estadoValue;
  }

  @Override
  public Integer integerValue() {
    return integerValue;
  }

  @Override
  public LocalDate localDate() {
    return localDate;
  }

  @Override
  public Long longValue() {
    return longValue;
  }

  @Override
  public String stringValue() {
    return stringValue;
  }

}