/*
 * SnakeYaml.java criado em 04/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc;

import static com.google.common.collect.Lists.transform;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class SnakeYamlConfig implements Config {

  private final Map<ConfigKey, Etc> model;

  private final Provider<Yaml> yaml;

  private final Injector injector;

  @Inject
  public SnakeYamlConfig(Map<ConfigKey, Etc> model,
                         Provider<Yaml> yamlProvider,
                         Injector injector) {
    this.model = model;
    this.yaml = yamlProvider;
    this.injector = injector;
  }

  @Override
  public Mapping load(Reader io) {
    Object load = yaml.get().load(io);
    return load0(load);
  }

  @Override
  public Mapping load(String text) {
    Object load = yaml.get().load(text);
    return load0(load);
  }

  @Override
  public Mapping load(InputStream input) {
    Object load = yaml.get().load(input);
    return load0(load);
  }

  @Override
  public <T> T load(Reader io, Class<T> type) {
    checkType(type);
    Object load = yaml.get().load(io);
    return load0(load, type);
  }

  @Override
  public <T> T load(String text, Class<T> type) {
    checkType(type);
    Object load = yaml.get().load(text);
    return load0(load, type);
  }

  @Override
  public <T> T load(InputStream input, Class<T> type) {
    checkType(type);
    Object load = yaml.get().load(input);
    return load0(load, type);
  }

  @Override
  public List<Mapping> loadAll(Reader io) {
    Object load = yaml.get().load(io);
    return loadAll0(load);
  }

  @Override
  public List<Mapping> loadAll(String text) {
    Object load = yaml.get().load(text);
    return loadAll0(load);
  }

  @Override
  public List<Mapping> loadAll(InputStream input) {
    Object load = yaml.get().load(input);
    return loadAll0(load);
  }

  @Override
  public <T> List<T> loadAll(Reader io, Class<T> type) {
    checkType(type);
    Object load = yaml.get().load(io);
    return loadAll0(load, type);
  }

  @Override
  public <T> List<T> loadAll(String text, Class<T> type) {
    checkType(type);
    Object load = yaml.get().load(text);
    return loadAll0(load, type);
  }

  @Override
  public <T> List<T> loadAll(InputStream input, Class<T> type) {
    checkType(type);
    Object load = yaml.get().load(input);
    return loadAll0(load, type);
  }

  @Override
  public void write(Object model, File file) throws IOException {
    String data = toString(model);
    Files.write(data, file, Charsets.UTF_8);
  }

  @Override
  public String toString(Object model) {
    return yaml.get().dump(model);
  }

  private void checkType(Class<?> type) {
    ConfigKey key = new ConfigKey(type);
    Preconditions.checkArgument(model.containsKey(key), "No mapping found for " + type);
  }

  private Mapping load0(Object load) {
    @SuppressWarnings("unchecked")
    Map<String, Object> map = (Map<String, Object>) load;
    return new SnakeYamlMapping(map);
  }

  @SuppressWarnings("unchecked")
  private <T> T load0(Object load, Class<T> type) {
    Map<String, Object> map = (Map<String, Object>) load;

    ConfigKey key = new ConfigKey(type);
    Etc etc = model.get(key);
    ConfigLoader<T> loader = (ConfigLoader<T>) injector.getInstance(etc.getLoader());

    return loader.load(new SnakeYamlMapping(map));
  }

  private List<Mapping> loadAll0(Object load) {
    return ToMapping.load(load);
  }

  @SuppressWarnings("unchecked")
  private <T> List<T> loadAll0(Object load, Class<T> type) {
    List<Map<String, Object>> list;

    if (List.class.isAssignableFrom(load.getClass())) {
      list = (List<Map<String, Object>>) load;
    } else {
      Map<String, Object> map = (Map<String, Object>) load;
      list = ImmutableList.of(map);
    }

    ConfigKey key = new ConfigKey(type);
    Etc etc = model.get(key);
    ConfigLoader<T> loader = (ConfigLoader<T>) injector.getInstance(etc.getLoader());

    return transform(list, new ToModel<T>(loader));
  }

  private static class ToModel<T> implements Function<Map<String, Object>, T> {

    private final ConfigLoader<T> loader;

    public ToModel(ConfigLoader<T> loader) {
      this.loader = loader;
    }

    @Override
    public T apply(Map<String, Object> map) {
      SnakeYamlMapping mapping = new SnakeYamlMapping(map);
      return loader.load(mapping);
    }

  }

}