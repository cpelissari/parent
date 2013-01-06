/*
 * ObjectosComunsYamlModel.java criado em 04/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc;

import org.yaml.snakeyaml.Yaml;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractEtcModule extends AbstractModule {

  private MapBinder<ConfigKey, Etc> modelBinder;

  @Override
  protected final void configure() {
    modelBinder = MapBinder.newMapBinder(binder(), ConfigKey.class, Etc.class);

    bind(Yaml.class).toProvider(SnakeYamlProvider.class);

    configureEtc();
  }

  protected abstract void configureEtc();

  protected <T> EtcBuilder<T> etc(Class<T> config) {
    return new EtcBuilder<T>(config);
  }

  protected class EtcBuilder<T> {

    private final Class<T> config;

    public EtcBuilder(Class<T> config) {
      this.config = Preconditions.checkNotNull(config);
    }

    public void withLoader(Class<? extends ConfigLoader<T>> loader) {
      Preconditions.checkNotNull(loader);
      Etc etc = new Etc(config, loader);
      modelBinder.addBinding(new ConfigKey(config)).toInstance(etc);
    }

  }

}