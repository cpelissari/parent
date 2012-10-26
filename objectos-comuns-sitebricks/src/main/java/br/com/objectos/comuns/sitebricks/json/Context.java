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
package br.com.objectos.comuns.sitebricks.json;

import static com.google.common.collect.Maps.newLinkedHashMap;

import java.util.Map;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Context {

  final Object root;

  final Map<String, Object> map = newLinkedHashMap();

  private Context() {
    this.root = null;
  }

  private Context(Object root) {
    this.root = root;
  }

  public static Context of() {
    return new Context();
  }
  public static Context of(Object root) {
    return new Context(root);
  }

  public void put(String key, Object value) {
    map.put(key, value);
  }

}