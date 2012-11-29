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
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractTimeoutScript<K, V> extends AbstractScript<K, V> {

  private final ScheduledExecutorService timeoutExecutor;

  private long timeout;
  private TimeUnit unit;

  public AbstractTimeoutScript(ExecutorService executor,
                               ScheduledExecutorService timeoutExecutor,
                               ConcurrentMap<K, Script<V>> map,
                               K key) {
    super(executor, map, key);
    this.timeoutExecutor = timeoutExecutor;

    configureTimeout();
  }

  protected void onTimeout(K key) {
  }

  protected abstract void configureTimeout();

  protected void cancelAfter(long timeout, TimeUnit unit) {
    this.timeout = timeout;
    this.unit = unit;
  }

  @Override
  void postSubmit(K key, Future<V> future) {
    TimeoutTask<K, V> task = new TimeoutTask<K, V>(key, future, this);
    timeoutExecutor.schedule(task, timeout, unit);
  }

  private static class TimeoutTask<K, V> implements Runnable {

    private final K key;

    private final Future<V> future;

    private final AbstractTimeoutScript<K, V> script;

    public TimeoutTask(K key, Future<V> future, AbstractTimeoutScript<K, V> script) {
      this.key = key;
      this.future = future;
      this.script = script;
    }

    @Override
    public void run() {
      if (!future.isDone()) {
        tryToCancel();
      }
    }

    private void tryToCancel() {
      try {
        script.onTimeout(key);
        script.cancel();
        script.onError(key,
            new TimeoutException("Script took longer than maximum allowed time for completion."));
      } finally {
        script.finish();
      }
    }

  }

}