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

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractScript<K, V> implements Script<V> {

  private final Object lock = new Object();

  private final ExecutorService executor;

  private final ConcurrentMap<K, Script<V>> map;
  private final K key;

  private Future<V> future;

  public AbstractScript(ExecutorService executor, ConcurrentMap<K, Script<V>> map, K key) {
    this.executor = executor;
    this.map = map;
    this.key = key;
  }

  @Override
  public boolean cancel() {
    boolean res = false;

    if (future != null) {
      res = future.cancel(true);
    }

    return res;
  }

  @Override
  public Future<V> get() {
    if (future == null) {
      synchronized (lock) {
        if (future == null) {
          future = executor.submit(this);
          postSubmit(key, future);
        }
      }
    }
    return future;
  }

  @Override
  public V call() throws Exception {
    try {
      onStart(key);
      V res = tryToCall(key);
      onSuccess(key, res);
      return res;

    } catch (Exception e) {
      onError(key, e);
      throw e;

    } finally {
      finish();

    }
  }

  protected void onStart(K key) {
  }

  protected void onSuccess(K key, V res) {
  }

  protected void onError(K key, Exception e) {
  }

  protected void onFinish(K key) {
  }

  void postSubmit(K key, Future<V> future) {
  }

  void finish() {
    map.remove(key);
    onFinish(key);
  }

  protected abstract V tryToCall(K key) throws Exception;

}