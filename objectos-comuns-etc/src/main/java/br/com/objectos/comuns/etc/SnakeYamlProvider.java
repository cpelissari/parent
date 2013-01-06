/*
 * Copyright 2012 Objectos, Fábrica de Software LTDA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package br.com.objectos.comuns.etc;

import static com.google.common.collect.Sets.newLinkedHashSet;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class SnakeYamlProvider implements Provider<Yaml> {

  private final Map<ConfigKey, Etc> yamlModel;

  @Inject
  public SnakeYamlProvider(Map<ConfigKey, Etc> yamlModel) {
    this.yamlModel = yamlModel;
  }

  @Override
  public Yaml get() {
    Representer representer = new Representer();

    for (ConfigKey key : yamlModel.keySet()) {
      representer.addClassTag(key.get(), Tag.MAP);
    }

    NaturalPropertyUtils propertyUtils = new NaturalPropertyUtils();
    representer.setPropertyUtils(propertyUtils);

    DumperOptions options = new DumperOptions();
    options.setDefaultFlowStyle(FlowStyle.BLOCK);

    return new org.yaml.snakeyaml.Yaml(
        new Constructor(),
        representer,
        options,
        new Resolver());
  }

  private static class NaturalPropertyUtils extends PropertyUtils {

    @Override
    protected Set<Property> createPropertySet(
        Class<? extends Object> type, BeanAccess bAccess)
        throws IntrospectionException {

      Collection<Property> props = getPropertiesMap(type, BeanAccess.FIELD).values();

      Set<Property> res = newLinkedHashSet();

      for (Property property : props) {
        if (property.isReadable()) {
          res.add(property);
        }
      }

      return res;

    }

  }

}