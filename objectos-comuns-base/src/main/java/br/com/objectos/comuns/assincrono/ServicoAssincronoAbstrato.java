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

import java.util.concurrent.Future;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class ServicoAssincronoAbstrato<T> implements
    ServicoAssincrono<T> {

  private final Agendador agendador;

  private final FabricaDeTarefa<T> fabricaDeTarefa;

  protected ServicoAssincronoAbstrato(Agendador agendador,
      FabricaDeTarefa<T> fabricaDeTarefa) {
    this.agendador = agendador;
    this.fabricaDeTarefa = fabricaDeTarefa;
  }

  public Agendamento<T> enviar(final Agendamento<T> agendamento) {
    Tarefa<T> tarefa = fabricaDeTarefa.obterDe(agendamento);

    final Future<T> future = agendador.agendar(tarefa);

    return new Agendamento.Atualizacoes<T>() {

      @Override
      public Agendamento<T> obterEntidadeAtualizada() {
        return agendamento.atualizar(this);
      }

      @Override
      public Future<T> getFuture() {
        return future;
      }

    }.obterEntidadeAtualizada();
  }

  @Override
  public void iniciar() {
    agendador.iniciar();
  }

  @Override
  public void terminar() {
    agendador.terminar();
  }

}