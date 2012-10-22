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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AgendamentoAbstrato<T> implements Agendamento<T> {

  private final Identificador<T> identificador;

  private final Configuracao configuracao;

  private Future<T> future;

  private Erro erro;

  public AgendamentoAbstrato(Identificador<T> identificador,
      Configuracao configuracao) {
    this.identificador = identificador;
    this.configuracao = configuracao;
  }

  @Override
  public Agendamento<T> atualizar(Atualizacoes<T> atualizacoes) {
    this.future = atualizacoes.getFuture();

    return this;
  }

  @Override
  public Identificador<T> getIdentificador() {
    return identificador;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <C extends Configuracao> C getConfiguracao() {
    return (C) configuracao;
  }

  @Override
  public Erro getErro() {
    return erro;
  }

  @Override
  public boolean isErroDuranteExecucao() {
    return erro != null;
  }

  @Override
  public boolean limparErroDeExecucao() {
    if (erro != null) {
      synchronized (erro) {
        erro = null;
        return true;
      }
    } else {
      return false;
    }
  }

  @Override
  public boolean isResultadoDisponivel() throws ExcecaoDeAgendamento {
    if (future == null) {
      return false;
    }

    try {

      T resultado = future.get(0, TimeUnit.MILLISECONDS);
      return resultado != null;

    } catch (InterruptedException e) {

      erro = obterErroDe(e);
      throw new ExcecaoDeAgendamento(e);

    } catch (ExecutionException e) {

      erro = obterErroDe(e);
      throw new ExcecaoDeAgendamento(e);

    } catch (TimeoutException e) {

      return false;

    }
  }

  @Override
  public T obterOuEsperar() throws ExcecaoDeAgendamento {
    try {

      return future.get();

    } catch (InterruptedException e) {

      erro = obterErroDe(e);
      throw new ExcecaoDeAgendamento(e);

    } catch (ExecutionException e) {

      erro = obterErroDe(e);
      throw new ExcecaoDeAgendamento(e);

    }
  }

  protected abstract Erro obterErroDe(ExecutionException e);

  protected abstract Erro obterErroDe(InterruptedException e);

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((configuracao == null) ? 0 : configuracao.hashCode());
    result = prime * result
        + ((identificador == null) ? 0 : identificador.hashCode());
    return result;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AgendamentoAbstrato other = (AgendamentoAbstrato) obj;
    if (configuracao == null) {
      if (other.configuracao != null)
        return false;
    } else if (!configuracao.equals(other.configuracao))
      return false;
    if (identificador == null) {
      if (other.identificador != null)
        return false;
    } else if (!identificador.equals(other.identificador))
      return false;
    return true;
  }

}