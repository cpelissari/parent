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

import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ErroGenerico implements Erro {

  private final List<String> mensagens;

  public ErroGenerico(Throwable throwable) {
    String mensagem = throwable.getMessage();
    if (mensagem != null) {
      this.mensagens = ImmutableList.of(mensagem);
    } else {
      this.mensagens = ImmutableList.of();
    }
    throwable.printStackTrace();
  }

  @Override
  public List<String> getMensagens() {
    return mensagens;
  }

}