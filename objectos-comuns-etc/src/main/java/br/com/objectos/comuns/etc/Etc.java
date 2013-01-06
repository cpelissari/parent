/*
 * Etc.java criado em 07/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software L?DA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class Etc {

  private final Class<?> config;

  private final Class<? extends ConfigLoader<?>> loader;

  public Etc(Class<?> config, Class<? extends ConfigLoader<?>> loader) {
    this.config = config;
    this.loader = loader;
  }

  public Class<?> getConfig() {
    return config;
  }

  public Class<? extends ConfigLoader<?>> getLoader() {
    return loader;
  }

}