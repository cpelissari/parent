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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AgendadorAbstrato implements Agendador {

  private final ExecutorService service;

  protected AgendadorAbstrato(ExecutorService service) {
    this.service = service;
  }

  public <T> Future<T> agendar(Callable<T> task) {
    return service.submit(task);
  }

  public <T> Future<T> agendar(Runnable task, T result) {
    return service.submit(task, result);
  }

  public Future<?> agendar(Runnable task) {
    return service.submit(task);
  }

  @Override
  public void iniciar() {
  }

  @Override
  public void terminar() {
    // as per JavaDoc

    service.shutdown(); // Disable new tasks from being submitted
    try {
      // Wait a while for existing tasks to terminate
      if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
        service.shutdownNow(); // Cancel currently executing tasks
        // Wait a while for tasks to respond to being cancelled
        if (!service.awaitTermination(60, TimeUnit.SECONDS))
          System.err.println("Pool did not terminate");
      }
    } catch (InterruptedException ie) {
      // (Re-)Cancel if current thread also interrupted
      service.shutdownNow();
      // Preserve interrupt status
      Thread.currentThread().interrupt();
    }
  }

}