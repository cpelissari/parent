/*
 * ConfigKey.java criado em 07/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ConfigKey {

  private final Class<?> clazz;

  public ConfigKey(Class<?> clazz) {
    this.clazz = clazz;
  }

  public Class<?> get() {
    return clazz;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    ConfigKey other = (ConfigKey) obj;
    return clazz.equals(other.clazz);
  }

}