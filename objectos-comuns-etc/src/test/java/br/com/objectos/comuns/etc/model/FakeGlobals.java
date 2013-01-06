/*
 * FakeGlobals.java criado em 04/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc.model;

import static br.com.objectos.comuns.etc.model.FakeDirs.DIRS_USER_A;
import static br.com.objectos.comuns.etc.model.FakeUsers.USER_A;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class FakeGlobals {

  public static final Global GLOBAL_USER_A = start()
      .user(USER_A)
      .dirs(DIRS_USER_A)
      .build();

  private static FakeGlobalBuilder start() {
    return new FakeGlobalBuilder();
  }

}