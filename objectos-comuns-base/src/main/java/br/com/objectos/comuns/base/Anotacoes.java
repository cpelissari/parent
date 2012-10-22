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
package br.com.objectos.comuns.base;

import java.lang.annotation.Annotation;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Anotacoes {

  private Anotacoes() {
  }

  public static <A extends Annotation> A obterAnotacaoDeInstancia(
      Class<A> classeDaAnotacao, Object objeto) {
    Class<?> classe = objeto.getClass();
    return obterAnotacaoDeClasse(classeDaAnotacao, classe);
  }

  public static <A extends Annotation> A obterAnotacaoDeClasse(
      Class<A> classeDaAnotacao, Class<?> classe) {
    A anotacao = null;

    anotacao = classe.getAnnotation(classeDaAnotacao);

    if (!classe.isInterface()) {
      anotacao = anotacao == null ? obterAnotacaoDoArray(classeDaAnotacao,
          classe.getInterfaces()) : anotacao;
    }

    if (anotacao != null) {
      return anotacao;
    }

    throw new IllegalArgumentException("Classe " + classe.getName()
        + " não possui anotação @" + classeDaAnotacao.getSimpleName());
  }

  private static <A extends Annotation> A obterAnotacaoDoArray(
      Class<A> classeDaAnotacao, Class<?>[] classes) {
    A anotacao = null;

    for (Class<?> candidata : classes) {
      if (candidata.isAnnotationPresent(classeDaAnotacao)) {
        anotacao = candidata.getAnnotation(classeDaAnotacao);
        break;
      }
    }

    return anotacao;
  }

}