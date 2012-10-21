/*
 * FakeEntityBuilder.java criado em 09/09/2012
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
public class FakeEntityBuilder implements Entity.Builder {

  private Boolean booleanValue;
  private Booleano booleanoValue;
  private Double doubleValue;
  private Estado estadoValue;
  private Integer integerValue;
  private LocalDate localDate;
  private Long longValue;
  private String stringValue;

  @Override
  public Entity build() {
    return new EntityPojo(this);
  }

  public FakeEntityBuilder booleanValue(Boolean booleanValue) {
    this.booleanValue = booleanValue;
    return this;
  }
  public FakeEntityBuilder booleanoValue(Booleano booleanoValue) {
    this.booleanoValue = booleanoValue;
    return this;
  }
  public FakeEntityBuilder doubleValue(Double doubleValue) {
    this.doubleValue = doubleValue;
    return this;
  }
  public FakeEntityBuilder estadoValue(Estado estadoValue) {
    this.estadoValue = estadoValue;
    return this;
  }
  public FakeEntityBuilder integerValue(Integer integerValue) {
    this.integerValue = integerValue;
    return this;
  }
  public FakeEntityBuilder localDate(LocalDate localDate) {
    this.localDate = localDate;
    return this;
  }
  public FakeEntityBuilder longValue(Long longValue) {
    this.longValue = longValue;
    return this;
  }
  public FakeEntityBuilder stringValue(String stringValue) {
    this.stringValue = stringValue;
    return this;
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