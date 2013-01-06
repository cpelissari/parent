/*
 * DirsLoader.java criado em 07/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc.model;

import br.com.objectos.comuns.etc.ConfigLoader;
import br.com.objectos.comuns.etc.Mapping;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class DirsLoader implements ConfigLoader<Dirs> {

  @Override
  public Dirs load(Mapping map) {
    return new DirsBuilder(map).build();
  }

  private class DirsBuilder implements Dirs.Builder {

    private final Mapping map;

    public DirsBuilder(Mapping map) {
      this.map = map;
    }

    @Override
    public Dirs build() {
      return new Dirs(this);
    }

    @Override
    public String getUserHome() {
      return map.getString("userHome");
    }

  }

}