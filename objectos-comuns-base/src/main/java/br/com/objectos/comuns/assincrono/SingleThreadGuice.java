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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.inject.Singleton;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
public class SingleThreadGuice implements SingleThread {

  private final ConcurrentMap<Object, Lock> locks = new ConcurrentHashMap<Object, Lock>();

  @Override
  public <T> T executar(Single<T> single) {
    T resultado = null;

    Object chave = single.getChave();
    Lock lock = obterLock(chave);
    try {
      lock.lock();
      resultado = single.executar();
    } finally {
      locks.remove(chave);
      lock.unlock();
    }

    return resultado;
  }

  private Lock obterLock(Object chave) {
    Lock lock = locks.get(chave);
    if (lock == null) {
      Lock newLock = new ReentrantLock();
      lock = locks.putIfAbsent(chave, newLock);
      if (lock == null) {
        lock = newLock;
      }
    }
    return lock;
  }

}