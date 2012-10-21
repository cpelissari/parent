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

import static com.google.common.collect.Lists.*;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ProgressoImpl extends Observable implements Progresso {

  private long inicio;

  private LinkedList<String> mensagens = newLinkedList();

  public ProgressoImpl() {
  }

  public ProgressoImpl(String mensagem) {
    this.mensagens.add(mensagem);
  }

  @Override
  public void adicionar(String mensagem) {
    synchronized (mensagens) {
      mensagens.add(0, mensagem);
    }
    setChanged();
    notifyObservers(mensagem);
  }

  @Override
  public String getMensagem() {
    return mensagens.isEmpty() ? "" : mensagens.get(0);
  }

  @Override
  public long getMilisegundos() {
    return delta() % 1000;
  }

  @Override
  public long getHoras() {
    return TimeUnit.NANOSECONDS.toHours(delta()) % 60;
  }

  @Override
  public long getMinutos() {
    return TimeUnit.NANOSECONDS.toMinutes(delta()) % 60;
  }

  @Override
  public long getSegundos() {
    return TimeUnit.NANOSECONDS.toSeconds(delta()) % 60;
  }

  @Override
  public void iniciar() {
    inicio = System.nanoTime();
  }

  void registrarObservador(Observer observador) {
    addObserver(observador);
  }

  void removerObservador(Observer observador) {
    deleteObserver(observador);
  }

  void removerObservadores() {
    deleteObservers();
  }

  private long delta() {
    return System.nanoTime() - inicio;
  }

}