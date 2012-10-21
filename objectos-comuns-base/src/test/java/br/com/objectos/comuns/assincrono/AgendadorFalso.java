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
import java.util.concurrent.Future;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AgendadorFalso implements Agendador {

  @Override
  public void iniciar() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void terminar() {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> Future<T> agendar(Callable<T> tarefa) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> Future<T> agendar(Runnable tarefa, T resultado) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Future<?> agendar(Runnable tarefa) {
    throw new UnsupportedOperationException();
  }

}