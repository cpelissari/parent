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

import static com.google.common.collect.Lists.*;
import static org.testng.Assert.*;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.com.objectos.comuns.assincrono.impl.IdTatu;
import br.com.objectos.comuns.assincrono.impl.Tatu;

import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TesteDeAnotadorDeProgresso extends TesteDeAssincronoAbstrato {

  private static final AtomicInteger contador = new AtomicInteger();

  @Inject
  private AnotadorDeProgresso anotador;

  private List<String> mensagens;

  @BeforeClass
  public void prepararClasse() {
    injectMembers();
  }

  @BeforeMethod
  public void prepararTeste() {
    mensagens = newArrayList();
  }

  public void verifiqueAnotarProgresso() {
    Identificador<Tatu> identificador = proximoIdentificador();

    Progresso antes = anotador.obterProgressoAtual(identificador);
    assertNull(antes);

    anotador.anotarProgresso(identificador, "OLÁ");

    Progresso depois = anotador.obterProgressoAtual(identificador);
    assertEquals(depois.getMensagem(), "OLÁ");
  }

  public void verifiqueAnotarProgressoExistente() {
    Identificador<Tatu> identificador = proximoIdentificador();

    anotador.anotarProgresso(identificador, "1");
    anotador.anotarProgresso(identificador, "2");
    anotador.anotarProgresso(identificador, "3");

    Progresso resultado = anotador.obterProgressoAtual(identificador);
    assertEquals(resultado.getMensagem(), "3");
  }

  public void verifiqueMarcarTerminado() {
    Identificador<Tatu> identificador = proximoIdentificador();

    anotador.anotarProgresso(identificador, "1");
    anotador.anotarProgresso(identificador, "2");

    anotador.marcarTerminado(identificador);
    Progresso resultado = anotador.obterProgressoAtual(identificador);
    assertNull(resultado);
  }

  public void verifiqueRegistrarObservador() {
    Identificador<Tatu> identificador = proximoIdentificador();

    anotador.anotarProgresso(identificador, "1");

    anotador.registrarObservador(identificador, new ObservadorFalso());

    anotador.anotarProgresso(identificador, "2");
    anotador.anotarProgresso(identificador, "3");

    assertEquals(mensagens.toString(), "[2, 3]");
  }

  public void verifiqueRegistrarObservadorAntesDeProgresso() {
    Identificador<Tatu> identificador = proximoIdentificador();

    anotador.registrarObservador(identificador, new ObservadorFalso());

    anotador.anotarProgresso(identificador, "1");
    anotador.anotarProgresso(identificador, "2");
    anotador.anotarProgresso(identificador, "3");

    assertEquals(mensagens.toString(), "[1, 2, 3]");
  }

  public void verifiqueRemoverObservador() {

  }

  private Identificador<Tatu> proximoIdentificador() {
    int id = contador.getAndIncrement();
    return new IdTatu(id);
  }

  private class ObservadorFalso implements Observer {

    private final int id = 0;

    @Override
    public void update(Observable o, Object mensagem) {
      String string = (String) mensagem;
      mensagens.add(string);
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + id;
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
      ObservadorFalso other = (ObservadorFalso) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (id != other.id)
        return false;
      return true;
    }

    private TesteDeAnotadorDeProgresso getOuterType() {
      return TesteDeAnotadorDeProgresso.this;
    }

  }

}