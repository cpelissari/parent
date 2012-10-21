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

import br.com.objectos.comuns.assincrono.Configuracao;
import br.com.objectos.comuns.base.Construtor;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ConfiguracaoDeTatu implements Configuracao {

  private final boolean forcar;

  private ConfiguracaoDeTatu(boolean forcar) {
    this.forcar = forcar;
  }

  public static ConstrutorDeConfig construtor() {
    return new ConstrutorDeConfig();
  }

  @Override
  public boolean isForcar() {
    return forcar;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (forcar ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ConfiguracaoDeTatu other = (ConfiguracaoDeTatu) obj;
    if (forcar != other.forcar)
      return false;
    return true;
  }

  public static class ConstrutorDeConfig implements
      Construtor<ConfiguracaoDeTatu> {

    private boolean forcar = false;

    public ConstrutorDeConfig forcar() {
      this.forcar = true;
      return this;
    }

    public ConstrutorDeConfig forcar(boolean forcar) {
      this.forcar = forcar;
      return this;
    }

    @Override
    public ConfiguracaoDeTatu novaInstancia() {
      return new ConfiguracaoDeTatu(forcar);
    }

  }

}
