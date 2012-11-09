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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AnotadorDeProgressoVazio implements AnotadorDeProgresso {

  @Override
  public void anotarProgresso(Identificador<?> identificador, String mensagem) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void iniciarProgresso(Identificador<?> identificador) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Progresso obterProgressoAtual(Identificador<?> identificador) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void marcarTerminado(Identificador<?> identificador) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void removerObservador(Identificador<?> identificador,
      Observer observador) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void registrarObservador(Identificador<?> identificador,
      Observer observador) {
    throw new UnsupportedOperationException();
  }

}