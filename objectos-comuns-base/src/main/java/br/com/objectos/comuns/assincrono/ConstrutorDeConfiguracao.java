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

import br.com.objectos.comuns.base.Construtor;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ConstrutorDeConfiguracao implements Construtor<Configuracao> {

  private boolean forcar = false;

  @Override
  public Configuracao novaInstancia() {
    return new ConfiguracaoImpl(forcar);
  }

  public ConstrutorDeConfiguracao forcar() {
    this.forcar = true;
    return this;
  }

  public ConstrutorDeConfiguracao naoForcar() {
    this.forcar = false;
    return this;
  }

  private static class ConfiguracaoImpl implements Configuracao {

    private final boolean forcar;

    public ConfiguracaoImpl(boolean forcar) {
      this.forcar = forcar;
    }

    @Override
    public boolean isForcar() {
      return forcar;
    }

  }

}