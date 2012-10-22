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

import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.inject.Singleton;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
public class AnotadorDeProgressoGuice implements AnotadorDeProgresso {

  private final ConcurrentMap<Identificador<?>, ProgressoImpl> progressos = new ConcurrentHashMap<Identificador<?>, ProgressoImpl>();

  @Override
  public void anotarProgresso(Identificador<?> identificador, String mensagem) {
    ProgressoImpl progresso = criarSeNecessario(identificador);
    progresso.adicionar(mensagem);
  }

  @Override
  public Progresso obterProgressoAtual(Identificador<?> identificador) {
    return progressos.get(identificador);
  }

  @Override
  public void iniciarProgresso(Identificador<?> identificador) {
    ProgressoImpl progresso = criarSeNecessario(identificador);
    progresso.iniciar();
  }

  @Override
  public void marcarTerminado(Identificador<?> identificador) {
    ProgressoImpl progresso = progressos.get(identificador);
    if (progresso != null) {
      progresso.adicionar("Agendamento terminado");
      progresso.removerObservadores();
      progressos.remove(identificador);
    }
  }

  @Override
  public void registrarObservador(Identificador<?> identificador,
      Observer observador) {
    ProgressoImpl progresso = criarSeNecessario(identificador);
    progresso.registrarObservador(observador);
  }

  @Override
  public void removerObservador(Identificador<?> identificador,
      Observer observador) {
    ProgressoImpl progresso = progressos.get(identificador);
    if (progresso != null) {
      progresso.removerObservador(observador);
    }
  }

  private ProgressoImpl criarSeNecessario(Identificador<?> identificador) {
    ProgressoImpl novoProgresso = new ProgressoImpl();
    ProgressoImpl progresso = progressos.putIfAbsent(identificador,
        novoProgresso);

    if (progresso != null) {
      return progresso;
    } else {
      return novoProgresso;
    }
  }

}