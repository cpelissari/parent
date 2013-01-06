/*
 * ToMapping.java criado em 07/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc;

import static com.google.common.collect.Lists.transform;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ToMapping implements Function<Map<String, Object>, Mapping> {

  @Override
  public Mapping apply(Map<String, Object> map) {
    return new SnakeYamlMapping(map);
  }

  @SuppressWarnings("unchecked")
  public static List<Mapping> load(Object load) {
    List<Map<String, Object>> list;

    if (List.class.isAssignableFrom(load.getClass())) {
      list = (List<Map<String, Object>>) load;
    } else {
      Map<String, Object> map = (Map<String, Object>) load;
      list = ImmutableList.of(map);
    }

    return transform(list, new ToMapping());
  }

}