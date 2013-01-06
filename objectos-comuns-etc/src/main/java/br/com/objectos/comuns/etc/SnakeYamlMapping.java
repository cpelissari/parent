/*
 * SnakeYamlMappgin.java criado em 07/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc;

import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class SnakeYamlMapping implements Mapping {

  private final Map<String, Object> map;

  public SnakeYamlMapping(Map<String, Object> map) {
    this.map = map;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Mapping getMapping(String key) {
    Preconditions.checkArgument(map.containsKey(key));

    Object object = map.get(key);
    return new SnakeYamlMapping((Map<String, Object>) object);
  }

  @Override
  public List<Mapping> getSequence(String key) {
    Preconditions.checkArgument(map.containsKey(key));

    Object object = map.get(key);
    return ToMapping.load(object);
  }

  @Override
  public String getString(String key) {
    Preconditions.checkArgument(map.containsKey(key));

    Object object = map.get(key);
    return object.toString();
  }

  @Override
  public boolean getBoolean(String key) {
    Preconditions.checkArgument(map.containsKey(key));

    Object object = map.get(key);
    return Boolean.class.cast(object).booleanValue();
  }

  @Override
  public int getInt(String key) {
    Preconditions.checkArgument(map.containsKey(key));

    Object object = map.get(key);
    return Integer.class.cast(object).intValue();
  }

  @Override
  public long getLong(String key) {
    Preconditions.checkArgument(map.containsKey(key));

    Object object = map.get(key);
    return Long.class.cast(object).longValue();
  }

  @Override
  public float getFloat(String key) {
    Preconditions.checkArgument(map.containsKey(key));

    Object object = map.get(key);
    return Double.class.cast(object).floatValue();
  }

  @Override
  public double getDouble(String key) {
    Preconditions.checkArgument(map.containsKey(key));

    Object object = map.get(key);
    return Double.class.cast(object).doubleValue();
  }

}