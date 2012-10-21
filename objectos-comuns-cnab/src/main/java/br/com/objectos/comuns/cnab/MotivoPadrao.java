/*
 * Copyright 2012 Objectos, Fábrica de Software LTDA.
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
package br.com.objectos.comuns.cnab;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class MotivoPadrao implements Motivo {

  private final String codigo;

  private final String descricao;

  public MotivoPadrao(String codigo, String descricao) {
    this.codigo = codigo;
    this.descricao = descricao;
  }

  @Override
  public String getCodigo() {
    return codigo;
  }

  @Override
  public String getDescricao() {
    return descricao;
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof MotivoPadrao)) {
      return false;
    }
    MotivoPadrao other = (MotivoPadrao) obj;
    if (codigo == null) {
      if (other.codigo != null) {
        return false;
      }
    } else if (!codigo.equals(other.codigo)) {
      return false;
    }
    return true;
  }

}