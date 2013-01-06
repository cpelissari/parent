/*
 * Dirs.java criado em 04/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc.model;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Dirs {

  public static interface Builder extends br.com.objectos.comuns.base.Builder<Dirs> {

    String getUserHome();

  }

  private final transient Dir workingDir;

  private final Dir userHome;

  Dirs(Builder builder) {
    workingDir = workingDir();

    String userHome = builder.getUserHome();
    this.userHome = new Dir(userHome);
  }

  public Dir getWorkingDir() {
    return workingDir;
  }

  public Dir getUserHome() {
    return userHome;
  }

  private Dir workingDir() {
    String path = System.getProperty("user.dir");
    return new Dir(path);
  }

}