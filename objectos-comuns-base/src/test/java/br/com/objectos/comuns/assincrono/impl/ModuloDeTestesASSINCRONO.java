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

import br.com.objectos.comuns.assincrono.Agenda;
import br.com.objectos.comuns.assincrono.FabricaDeTarefa;
import br.com.objectos.comuns.assincrono.ModuloCOMUNS_BASE_ASSINCRONO;
import br.com.objectos.comuns.assincrono.Secretaria;
import br.com.objectos.comuns.assincrono.ServicoAssincrono;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ModuloDeTestesASSINCRONO extends AbstractModule {

  @Override
  protected void configure() {
    bind(new TypeLiteral<Agenda<Tatu>>() {
    }).to(AgendaDeTatu.class);

    bind(new TypeLiteral<FabricaDeTarefa<Tatu>>() {
    }).to(FabricaDeTarefaDeTatu.class);

    bind(new TypeLiteral<Secretaria<Tatu>>() {
    }).to(SecretariaDeTatu.class);
    bind(new TypeLiteral<ServicoAssincrono<Tatu>>() {
    }).to(ServicoAssincronoDeTatu.class);

    install(new ModuloCOMUNS_BASE_ASSINCRONO());
  }

}