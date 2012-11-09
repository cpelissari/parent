/*
 * FakeEntities.java criado em 09/09/2012
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
public class FakeEntities {

  public static final Entity LINE_1 = start()
      .booleanValue(true)
      .booleanoValue(Booleano.VERDADEIRO)
      .doubleValue(1.23)
      .estadoValue(Estado.SP)
      .integerValue(123)
      .localDate(new LocalDate(2012, 9, 9))
      .longValue(123l)
      .stringValue("abc")
      .build();

  public static final Entity LINE_2 = start()
      .booleanValue(false)
      .booleanoValue(Booleano.FALSO)
      .doubleValue(4.56)
      .estadoValue(Estado.MG)
      .integerValue(456)
      .localDate(new LocalDate(2011, 9, 9))
      .longValue(456l)
      .stringValue("def")
      .build();

  private FakeEntities() {
  }

  private static FakeEntityBuilder start() {
    return new FakeEntityBuilder();
  }

}