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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.objectos.comuns.assincrono.Agendador;
import br.com.objectos.comuns.assincrono.ModuloCOMUNS_BASE_ASSINCRONO;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TesteDeAgendador {

  private Injector injector;

  @BeforeClass
  public void prepararInjector() {
    injector = Guice.createInjector(new ModuloCOMUNS_BASE_ASSINCRONO());
  }

  public void verifiqueNumeroDeThreadsDoExecutorService() {
    ExecutorService executorService = injector
        .getInstance(ExecutorService.class);

    ThreadPoolExecutor executor = (ThreadPoolExecutor) executorService;

    int cpus = Runtime.getRuntime().availableProcessors();

    assertEquals(executor.getMaximumPoolSize(), cpus + 1);
  }

  public void verifiqueQueAgendadorTerminaSemExcecoes() {
    Agendador agendador = injector.getInstance(Agendador.class);

    agendador.terminar();

    assertTrue(true);
  }

}