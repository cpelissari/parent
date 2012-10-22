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
package br.com.objectos.comuns.util.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;

import com.google.common.base.Supplier;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractServer<K, V> {

  protected final ConcurrentMap<K, Script<V>> map = new ConcurrentHashMap<K, Script<V>>();

  protected Future<V> submit(K key, Supplier<Script<V>> supplier) {
    Script<V> script = map.get(key);

    if (script == null) {

      Script<V> newScript = supplier.get();
      script = map.putIfAbsent(key, newScript);

      if (script == null) {
        script = newScript;
      }

    }

    return script.get();
  }

}