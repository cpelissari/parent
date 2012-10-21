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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class SecretariaAbstrata<T> implements Secretaria<T> {

  private final Agenda<T> agenda;

  private final AnotadorDeProgresso anotadorDeProgresso;

  private final ServicoAssincrono<T> servicoAssincrono;

  protected SecretariaAbstrata(Agenda<T> agenda,
      AnotadorDeProgresso anotadorDeProgresso,
      ServicoAssincrono<T> servicoAssincrono) {
    this.agenda = agenda;
    this.anotadorDeProgresso = anotadorDeProgresso;
    this.servicoAssincrono = servicoAssincrono;
  }

  @Override
  public Agendamento<T> agendar(Identificador<T> identificador,
      Configuracao configuracao) {
    T objeto = buscarPor(identificador);

    if (objeto == null || configuracao.isForcar()) {
      return agendarSeNecessario(identificador, configuracao);
    } else {
      return new AgendamentoImediato<T>(identificador, objeto, configuracao);
    }
  }

  protected abstract T buscarPor(Identificador<T> identificador);

  protected abstract Agendamento<T> novoAgendamento(
      Identificador<T> identificador, Configuracao configuracao);

  private Agendamento<T> agendarSeNecessario(Identificador<T> identificador,
      Configuracao configuracao) {
    Agendamento<T> novoAgendamento = novoAgendamento(identificador,
        configuracao);

    Agendamento<T> existente = agenda.obterAgendamentoDe(identificador);

    if (existente != null) {
      if (existente.limparErroDeExecucao()) {
        // TODO isto provavelmente deveria ser uma op. atômica
        anotadorDeProgresso.iniciarProgresso(identificador);
        anotadorDeProgresso.anotarProgresso(identificador, "Agendamento feito");
        existente = servicoAssincrono.enviar(existente);
      }

      return existente;
    } else {
      Agendamento<T> agendamento = agenda.agendarSeNecessario(novoAgendamento);

      if (agendamento == null) {
        // TODO isto provavelmente deveria ser uma op. atômica
        anotadorDeProgresso.iniciarProgresso(identificador);
        anotadorDeProgresso.anotarProgresso(identificador, "Agendamento feito");
        agendamento = servicoAssincrono.enviar(novoAgendamento);
      }

      return agendamento;
    }
  }

  private static class AgendamentoImediato<T> implements Agendamento<T> {

    private final Identificador<T> identificador;

    private final T objeto;

    private final Configuracao configuracao;

    public AgendamentoImediato(Identificador<T> identificador, T objeto,
        Configuracao configuracao) {
      this.identificador = identificador;
      this.objeto = objeto;
      this.configuracao = configuracao;
    }

    @Override
    public Agendamento<T> atualizar(Atualizacoes<T> atualizacoes) {
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
      return null;
    }

    @Override
    public boolean isErroDuranteExecucao() {
      return false;
    }

    @Override
    public boolean limparErroDeExecucao() {
      return false;
    }

    @Override
    public boolean isResultadoDisponivel() throws ExcecaoDeAgendamento {
      return true;
    }

    @Override
    public T obterOuEsperar() throws ExcecaoDeAgendamento {
      return objeto;
    }

  }

}