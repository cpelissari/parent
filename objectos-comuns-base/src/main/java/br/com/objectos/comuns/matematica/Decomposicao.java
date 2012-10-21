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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Decomposicao {

  private Decomposicao() {
  }

  public static int[] base10Inversa(long valor) {
    // as per Long.MAX_VALUE 9223372036854775807L, um long na base 10 pode ter
    // 19 números no máximo
    return base10Inversa(valor, 19);
  }

  public static int[] base10Inversa(long valor, int max) {
    int[] resultado = new int[max];

    for (int exp = 0; exp < resultado.length; exp++) {
      long valorIteracao = valor;

      for (int j = 0; j < exp; j++) {
        valorIteracao -= resultado[j];
      }

      long base = (long) Math.pow(10, exp);

      valorIteracao /= base;

      long resto = valorIteracao % 10;
      resultado[exp] = (int) resto;
    }

    return resultado;
  }

}