/*
 * Copyright 2011 Objectos, Fábrica de Software LTDA.
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
package br.com.objectos.comuns.assincrono;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class FutureFalso<V> implements Future<V> {

  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isCancelled() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isDone() {
    throw new UnsupportedOperationException();
  }

  @Override
  public V get() throws InterruptedException, ExecutionException {
    throw new UnsupportedOperationException();
  }

  @Override
  public V get(long timeout, TimeUnit unit) throws InterruptedException,
      ExecutionException, TimeoutException {
    throw new UnsupportedOperationException();
  }

}