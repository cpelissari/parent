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

import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.com.objectos.comuns.assincrono.impl.ConfiguracaoDeTatu;
import br.com.objectos.comuns.assincrono.impl.SecretariaDeTatu;
import br.com.objectos.comuns.assincrono.impl.Tatu;

import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TesteDeSecretariaSincrono extends TesteDeAssincronoAbstrato {

  private static final AtomicInteger contador = new AtomicInteger(876611);

  @Inject
  private Agenda<Tatu> agenda;

  @Inject
  private Secretaria<Tatu> secretaria;

  @BeforeClass
  public void prepararClasse() {
    injectMembers();
  }

  @BeforeMethod
  public void prepararTeste() {
    naoRetornarTatus();
  }

  public void verifiqueQueNaoHaAgendamentoQuandoTatuExiste()
      throws ExcecaoDeAgendamento {
    retornarTatus();

    Identificador<Tatu> identificador = proximoIdentificador();
    Configuracao configuracao = configuracaoPadrao();

    Agendamento<Tatu> agendamento = secretaria.agendar(identificador,
        configuracao);

    Tatu tatu = agendamento.obterOuEsperar();
    assertEquals(tatu.getIdentificador(), identificador);

    Agendamento<Tatu> agendamentoDaAgenda = agenda
        .obterAgendamentoDe(identificador);
    assertNull(agendamentoDaAgenda);
  }

  public void verifiqueNovoAgendamento() throws ExcecaoDeAgendamento {
    Identificador<Tatu> identificador = proximoIdentificador();
    Configuracao configuracao = configuracaoPadrao();

    Agendamento<Tatu> agendamento = secretaria.agendar(identificador,
        configuracao);

    Tatu tatu = agendamento.obterOuEsperar();
    assertEquals(tatu.getIdentificador(), identificador);

    Agendamento<Tatu> agendamentoDaAgenda = agenda
        .obterAgendamentoDe(identificador);
    assertNotNull(agendamentoDaAgenda);
  }

  public void verifiqueReagendamento() throws ExcecaoDeAgendamento {
    retornarTatus();

    Identificador<Tatu> identificador = proximoIdentificador();
    Configuracao configuracao = configuracaoForcar();

    Agendamento<Tatu> agendamento = secretaria.agendar(identificador,
        configuracao);

    Tatu tatu = agendamento.obterOuEsperar();
    assertEquals(tatu.getIdentificador(), identificador);

    Agendamento<Tatu> agendamentoDaAgenda = agenda
        .obterAgendamentoDe(identificador);
    assertNotNull(agendamentoDaAgenda);
  }

  private Identificador<Tatu> proximoIdentificador() {
    int id = contador.getAndIncrement();

    Tatu tatu = new Tatu(id);
    return tatu.getIdentificador();
  }

  private Configuracao configuracaoForcar() {
    return ConfiguracaoDeTatu.construtor().forcar().novaInstancia();
  }

  private Configuracao configuracaoPadrao() {
    return ConfiguracaoDeTatu.construtor().novaInstancia();
  }

  private void naoRetornarTatus() {
    ((SecretariaDeTatu) secretaria).naoRetornarTatus();
  }

  private void retornarTatus() {
    ((SecretariaDeTatu) secretaria).retornarTatus();
  }

}