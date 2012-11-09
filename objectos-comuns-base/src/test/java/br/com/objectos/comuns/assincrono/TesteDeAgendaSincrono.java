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
import org.testng.annotations.Test;

import br.com.objectos.comuns.assincrono.impl.AgendamentoDeTatu;
import br.com.objectos.comuns.assincrono.impl.ConfiguracaoDeTatu;
import br.com.objectos.comuns.assincrono.impl.Tatu;

import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TesteDeAgendaSincrono extends TesteDeAssincronoAbstrato {

  private static final AtomicInteger contador = new AtomicInteger();

  @Inject
  private Agenda<Tatu> agenda;

  @BeforeClass
  public void prepararClasse() {
    injectMembers();
  }

  public void verifiqueAgendamento() {
    Agendamento<Tatu> agendamento = proximoAgendamento();

    Agendamento<Tatu> existente = agenda.agendarSeNecessario(agendamento);

    assertNull(existente);
  }

  public void verifiqueQueNaoOcorreDuploAgendamento() {
    Agendamento<Tatu> existente = proximoAgendamento();

    Agendamento<Tatu> resultado = agenda.agendarSeNecessario(existente);
    assertNull(resultado);

    resultado = agenda.agendarSeNecessario(existente);
    assertEquals(resultado, existente);
  }

  public void verifiqueObterAgendamento() {
    Agendamento<Tatu> existente = proximoAgendamento();
    Identificador<Tatu> idExistente = existente.getIdentificador();

    Agendamento<Tatu> resultado = agenda.agendarSeNecessario(existente);
    assertNull(resultado);

    resultado = agenda.obterAgendamentoDe(idExistente);
    assertEquals(resultado, existente);
  }

  public void verifiqueTerminoDeAgendamento() {
    Agendamento<Tatu> existente = proximoAgendamento();
    Identificador<Tatu> idExistente = existente.getIdentificador();

    Agendamento<Tatu> resultado = agenda.agendarSeNecessario(existente);
    assertNull(resultado);

    agenda.marcarTerminado(existente);

    resultado = agenda.obterAgendamentoDe(idExistente);
    assertNull(resultado);
  }

  private Agendamento<Tatu> proximoAgendamento() {
    int id = contador.getAndIncrement();

    Tatu tatu = new Tatu(id);
    Identificador<Tatu> identificador = tatu.getIdentificador();
    Configuracao configuracao = ConfiguracaoDeTatu.construtor().novaInstancia();
    return new AgendamentoDeTatu(identificador, configuracao);
  }

}