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

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class NamedThreadFactory implements ThreadFactory {

  private final ThreadGroup group;

  private final AtomicInteger threadNumber = new AtomicInteger(1);

  private final String namePrefix;

  public NamedThreadFactory(String name) {
    SecurityManager s = System.getSecurityManager();
    group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    namePrefix = name;
  }

  @Override
  public Thread newThread(Runnable r) {
    String name = String.format("%s-%03d", namePrefix, threadNumber.getAndIncrement());
    Thread t = new Thread(group, r, name, 0);

    if (t.isDaemon()) {
      t.setDaemon(false);
    }
    if (t.getPriority() != Thread.NORM_PRIORITY) {
      t.setPriority(Thread.NORM_PRIORITY);
    }

    return t;
  }

}