/*
 * UserLoader.java criado em 07/09/2012
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
public class UserLoader implements ConfigLoader<User> {

  @Override
  public User load(Mapping map) {
    return new UserBuilder(map).build();
  }

  private class UserBuilder implements User.Builder {

    private final Mapping map;

    public UserBuilder(Mapping map) {
      this.map = map;
    }

    @Override
    public User build() {
      return new User(this);
    }

    @Override
    public String getName() {
      return map.getString("name");
    }

    @Override
    public String getEmail() {
      return map.getString("email");
    }

  }

}