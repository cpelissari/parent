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

import static org.testng.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.com.objectos.comuns.assincrono.impl.AgendamentoDeTatu;
import br.com.objectos.comuns.assincrono.impl.ConfiguracaoDeTatu;
import br.com.objectos.comuns.assincrono.impl.ErroDeExecucao;
import br.com.objectos.comuns.assincrono.impl.ErroDeInterrupcao;
import br.com.objectos.comuns.assincrono.impl.IdTatu;
import br.com.objectos.comuns.assincrono.impl.Tatu;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TesteDeAgendamento {

  private static final AtomicInteger contador = new AtomicInteger();

  private Tatu tatu;

  private ExecutionException executionException;

  private InterruptedException interruptedException;

  private TimeoutException timeoutException;

  @BeforeMethod
  public void prepararTeste() {
    tatu = null;
    naoLancaExcecao();
  }

  public void verifiqueContratoEquals() {
    assertEquals(tatu(0, true), tatu(0, true));
    assertEquals(tatu(0, false), tatu(0, false));
    assertEquals(tatu(1, true), tatu(1, true));
    assertEquals(tatu(1, false), tatu(1, false));

    assertNotEquals(tatu(0, true), tatu(1, true));
    assertNotEquals(tatu(1, true), tatu(0, true));
    assertNotEquals(tatu(0, true), tatu(0, false));
    assertNotEquals(tatu(0, false), tatu(0, true));

    assertNotEquals(tatu(0, true), null);
    assertNotEquals(tatu(0, true), "");
  }

  public void verifiqueNovoAgendamento() throws ExcecaoDeAgendamento {
    Agendamento<Tatu> novoAgendamento = tatu(0, false);

    assertFalse(novoAgendamento.isErroDuranteExecucao());
    assertFalse(novoAgendamento.limparErroDeExecucao());
    assertFalse(novoAgendamento.isResultadoDisponivel());

    assertEquals(novoAgendamento.getIdentificador(), new IdTatu(0));
    assertNotNull(novoAgendamento.getConfiguracao());

    assertNull(novoAgendamento.getErro());
  }

  public void verifiqueObterOuEsperar() throws ExcecaoDeAgendamento {
    Agendamento<Tatu> agendamento = comFuturo(proximoAgendamento());

    Tatu resultado = agendamento.obterOuEsperar();

    assertEquals(resultado, tatu);
  }

  public void verifiqueObterOuEsperarErroDeExecucao() {
    Agendamento<Tatu> agendamento = comFuturo(proximoAgendamento());

    try {

      lancaExecutionException();
      agendamento.obterOuEsperar();

      // se chegar aqui o teste falhou.
      assertTrue(false);

    } catch (ExcecaoDeAgendamento e) {

      verifiqueErroDeExecucao(agendamento);

    }

  }

  public void verifiqueObterOuEsperarErroDeInterrupcao() {
    Agendamento<Tatu> agendamento = comFuturo(proximoAgendamento());

    try {

      lancaInterruptedException();
      agendamento.obterOuEsperar();

      // se chegar aqui o teste falhou.
      assertTrue(false);

    } catch (ExcecaoDeAgendamento e) {

      verifiqueErroDeInterrupcao(agendamento);

    }

  }

  public void verifiqueLimparErro() {
    Agendamento<Tatu> agendamento = comFuturo(proximoAgendamento());

    try {

      lancaInterruptedException();
      agendamento.obterOuEsperar();

    } catch (ExcecaoDeAgendamento e) {

      assertTrue(agendamento.isErroDuranteExecucao());
      assertNotNull(agendamento.getErro());

      assertTrue(agendamento.limparErroDeExecucao());

      assertFalse(agendamento.isErroDuranteExecucao());
      assertNull(agendamento.getErro());

    }
  }

  public void verifiqueResultadoDisponivel() throws ExcecaoDeAgendamento {
    Agendamento<Tatu> agendamento = comFuturo(proximoAgendamento());

    lancaTimeoutException();
    assertFalse(agendamento.isResultadoDisponivel());

    naoLancaExcecao();
    assertTrue(agendamento.isResultadoDisponivel());
  }

  public void verifiqueResultadoDisponivelErroDeExecucao() {
    Agendamento<Tatu> agendamento = comFuturo(proximoAgendamento());

    try {

      lancaExecutionException();
      assertFalse(agendamento.isResultadoDisponivel());

      // se chegar aqui o teste falhou.
      assertTrue(false);

    } catch (ExcecaoDeAgendamento e) {

      verifiqueErroDeExecucao(agendamento);

    }
  }

  public void verifiqueResultadoDisponivelErroDeInterrupcao() {
    Agendamento<Tatu> agendamento = comFuturo(proximoAgendamento());

    try {

      lancaInterruptedException();
      assertFalse(agendamento.isResultadoDisponivel());

      // se chegar aqui o teste falhou.
      assertTrue(false);

    } catch (ExcecaoDeAgendamento e) {

      verifiqueErroDeInterrupcao(agendamento);

    }
  }

  private void assertNotEquals(Object resultado, Object esperado) {
    assertFalse(resultado.equals(esperado));
  }

  private Agendamento<Tatu> comFuturo(final Agendamento<Tatu> agendamento) {
    tatu = new Tatu(0);

    return new Agendamento.Atualizacoes<Tatu>() {

      @Override
      public Future<Tatu> getFuture() {
        return new FuturoDeTatu();
      }

      @Override
      public Agendamento<Tatu> obterEntidadeAtualizada() {
        return agendamento.atualizar(this);
      }

    }.obterEntidadeAtualizada();
  }

  private void naoLancaExcecao() {
    executionException = null;
    interruptedException = null;
    timeoutException = null;
  }

  private void lancaExecutionException() {
    naoLancaExcecao();
    executionException = new ExecutionException(null);
  }

  private void lancaInterruptedException() {
    naoLancaExcecao();
    interruptedException = new InterruptedException();
  }

  private void lancaTimeoutException() {
    naoLancaExcecao();
    timeoutException = new TimeoutException();
  }

  private Agendamento<Tatu> proximoAgendamento() {
    int id = contador.getAndIncrement();
    return tatu(id, false);
  }

  private void verifiqueErroDeExecucao(Agendamento<Tatu> agendamento) {
    assertTrue(agendamento.isErroDuranteExecucao());
    Erro erro = agendamento.getErro();

    assertTrue(erro instanceof ErroDeExecucao);
  }

  private void verifiqueErroDeInterrupcao(Agendamento<Tatu> agendamento) {
    assertTrue(agendamento.isErroDuranteExecucao());
    Erro erro = agendamento.getErro();

    assertTrue(erro instanceof ErroDeInterrupcao);
  }

  private Agendamento<Tatu> tatu(int id, boolean forcar) {
    Identificador<Tatu> identificador = new IdTatu(id);
    Configuracao configuracao = ConfiguracaoDeTatu.construtor().forcar(forcar)
        .novaInstancia();
    return new AgendamentoDeTatu(identificador, configuracao);
  }

  private Tatu retornarOuLancarExcecao() throws InterruptedException,
      ExecutionException {
    if (executionException != null) {
      throw executionException;
    }

    if (interruptedException != null) {
      throw interruptedException;
    }

    return tatu;
  }

  private class FuturoDeTatu implements Future<Tatu> {

    @Override
    public Tatu get() throws InterruptedException, ExecutionException {
      return retornarOuLancarExcecao();
    }

    @Override
    public Tatu get(long timeout, TimeUnit unit) throws InterruptedException,
        ExecutionException, TimeoutException {
      if (timeoutException != null) {
        throw timeoutException;
      }

      return retornarOuLancarExcecao();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCancelled() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDone() {
      throw new UnsupportedOperationException();
    }

  }

}