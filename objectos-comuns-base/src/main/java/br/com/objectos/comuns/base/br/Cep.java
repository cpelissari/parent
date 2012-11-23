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
package br.com.objectos.comuns.base.br;

import static java.lang.String.format;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class Cep {

  private static final Pattern regex = Pattern.compile("^([0-9]{5})-?([0-9]{3})$");

  private static final Cep VAZIO = new CepVazio();

  private final int prefixo;

  private final short sufixo;

  private Cep(int prefixo, int sufixo) {
    this.prefixo = prefixo;
    this.sufixo = Integer.valueOf(sufixo).shortValue();
  }

  public static Cep vazio() {
    return VAZIO;
  }

  public static Cep valueOf(String text) {
    text = Strings.nullToEmpty(text).trim();
    Matcher matcher = regex.matcher(text);
    checkArgument(matcher.matches(), "CEP deve estar no format xxxxx-xxx");

    String _prefixo = matcher.group(1);
    String _sufixo = matcher.group(2);

    int prefixo = Integer.valueOf(_prefixo, 10).intValue();
    short sufixo = Short.valueOf(_sufixo, 10).shortValue();

    return new CepPadrao(prefixo, sufixo);
  }

  public static Cep valueOf(int val) {
    int temp = val;

    int sufixo = val % 1000;
    temp = temp / 1000;

    int prefixo = temp;

    return new CepPadrao(prefixo, sufixo);
  }

  private static class CepPadrao extends Cep {

    public CepPadrao(int prefixo, int sufixo) {
      super(prefixo, sufixo);

      checkArgument(prefixo >= 0, "Prefixo deve ser positivo");
      checkArgument(prefixo < 100000, "Prefixo deve ser um número entre 0 e 99999");
      checkArgument(sufixo >= 0, "Sufixo deve ser um número entre 0 e 999");
      checkArgument(sufixo < 1000, "Sufixo deve ser um número entre 0 e 999");
    }

  }

  private static class CepVazio extends Cep {

    public CepVazio() {
      super(0, 0);
    }

    @Override
    public String toString() {
      return "";
    }

  }

  private static void checkArgument(
      boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
    if (!expression) {
      throw new ExcecaoDeCepInvalido(format(errorMessageTemplate, errorMessageArgs));
    }
  }

  public int getPrefixo() {
    return prefixo;
  }

  public int getSufixo() {
    return sufixo;
  }

  @Override
  public String toString() {
    return String.format("%05d-%03d", prefixo, sufixo);
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + prefixo;
    result = prime * result + sufixo;
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
    if (!(obj instanceof Cep)) {
      return false;
    }
    Cep other = (Cep) obj;
    if (prefixo != other.prefixo) {
      return false;
    }
    if (sufixo != other.sufixo) {
      return false;
    }
    return true;
  }

}