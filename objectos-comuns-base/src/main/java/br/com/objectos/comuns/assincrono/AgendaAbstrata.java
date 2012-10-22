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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AgendaAbstrata<T> implements Agenda<T> {

  private final ConcurrentMap<Identificador<T>, Agendamento<T>> agendamentos = new ConcurrentHashMap<Identificador<T>, Agendamento<T>>();

  private final AnotadorDeProgresso anotadorDeProgresso;

  protected AgendaAbstrata(AnotadorDeProgresso anotadorDeProgresso) {
    this.anotadorDeProgresso = anotadorDeProgresso;
  }

  @Override
  public Agendamento<T> agendarSeNecessario(Agendamento<T> agendamento) {
    Identificador<T> identificador = agendamento.getIdentificador();

    Agendamento<T> existente = agendamentos.get(identificador);

    if (existente != null && existente.isErroDuranteExecucao()) {
      return agendamentos.replace(identificador, agendamento);
    } else {
      return agendamentos.putIfAbsent(identificador, agendamento);
    }
  }

  @Override
  public Agendamento<T> obterAgendamentoDe(Identificador<T> identificador) {
    return agendamentos.get(identificador);
  }

  @Override
  public void marcarTerminado(Agendamento<T> agendamento) {
    Identificador<T> identificador = agendamento.getIdentificador();
    agendamentos.remove(identificador);
    anotadorDeProgresso.marcarTerminado(identificador);
  }

  @Override
  public void sinalizarErro(Agendamento<T> agendamento) {
    Identificador<T> identificador = agendamento.getIdentificador();
    anotadorDeProgresso.anotarProgresso(identificador,
        "Agendamento iterrompido! Um erro inesperado aconteceu.");
  }

}