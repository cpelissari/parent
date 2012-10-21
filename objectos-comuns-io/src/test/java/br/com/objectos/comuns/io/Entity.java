/*
 * Pojo.java criado em 09/09/2012
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
public interface Entity {

  interface Builder extends br.com.objectos.comuns.base.Builder<Entity> {

    Boolean booleanValue();

    Booleano booleanoValue();

    Double doubleValue();

    Estado estadoValue();

    Integer integerValue();

    LocalDate localDate();

    Long longValue();

    String stringValue();

  }

  Boolean booleanValue();

  Booleano booleanoValue();

  Double doubleValue();

  Estado estadoValue();

  Integer integerValue();

  LocalDate localDate();

  Long longValue();

  String stringValue();

}