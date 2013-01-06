/*
 * GlobalLoader.java criado em 07/09/2012
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
public class GlobalLoader implements ConfigLoader<Global> {

  @Override
  public Global load(Mapping map) {
    return new GlobalBuilder(map).build();
  }

  private class GlobalBuilder implements Global.Builder {

    private final Mapping map;

    public GlobalBuilder(Mapping map) {
      this.map = map;
    }

    @Override
    public Global build() {
      return new Global(this);
    }

    @Override
    public User getUser() {
      Mapping userMap = map.getMapping("user");
      return new UserLoader().load(userMap);
    }

    @Override
    public Dirs getDirs() {
      Mapping dirsMap = map.getMapping("dirs");
      return new DirsLoader().load(dirsMap);
    }

  }

}