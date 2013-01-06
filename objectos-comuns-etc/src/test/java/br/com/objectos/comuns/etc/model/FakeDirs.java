/*
 * FakeDirs.java criado em 04/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc.model;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class FakeDirs {

  public static Dirs DIRS_USER_A = start()
      .userHome("/tmp/usera")
      .build();

  private static FakeDirsBuilder start() {
    return new FakeDirsBuilder();
  }

}