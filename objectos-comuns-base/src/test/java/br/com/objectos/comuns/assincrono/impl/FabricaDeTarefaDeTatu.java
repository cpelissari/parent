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
package br.com.objectos.comuns.assincrono.impl;

import br.com.objectos.comuns.assincrono.Agendamento;
import br.com.objectos.comuns.assincrono.FabricaDeTarefa;
import br.com.objectos.comuns.assincrono.Identificador;
import br.com.objectos.comuns.assincrono.Tarefa;

import com.google.inject.Singleton;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
public class FabricaDeTarefaDeTatu implements FabricaDeTarefa<Tatu> {

  @Override
  public Tarefa<Tatu> obterDe(Agendamento<Tatu> agendamento) {
    Identificador<Tatu> identificador = agendamento.getIdentificador();
    return new TarefaDeTatu(identificador);
  }

}