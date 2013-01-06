/*
 * FakeDirsBuilder.java criado em 04/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc.model;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class FakeDirsBuilder implements Dirs.Builder {

  private String userHome;

  @Override
  public Dirs build() {
    return new Dirs(this);
  }

  public FakeDirsBuilder userHome(String userHome) {
    this.userHome = userHome;
    return this;
  }

  @Override
  public String getUserHome() {
    return userHome;
  }

}