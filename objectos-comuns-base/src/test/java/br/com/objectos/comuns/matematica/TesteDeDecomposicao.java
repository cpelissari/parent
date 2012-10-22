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
package br.com.objectos.comuns.matematica;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TesteDeDecomposicao {

  public void verifiqueBase10() {
    long valor = 123456789;

    int[] resultado = Decomposicao.base10Inversa(valor);

    assertEquals(resultado[0], 9);
    assertEquals(resultado[1], 8);
    assertEquals(resultado[2], 7);
    assertEquals(resultado[3], 6);
    assertEquals(resultado[4], 5);
    assertEquals(resultado[5], 4);
    assertEquals(resultado[6], 3);
    assertEquals(resultado[7], 2);
    assertEquals(resultado[8], 1);
  }

}