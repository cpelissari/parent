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
package br.com.objectos.comuns.matematica.financeira;

import com.google.common.base.Function;

/**
 * @author mendo
 * 
 * @version $Id$
 */
public class FuncaoDePonderacao<P extends Ponderavel<T>, T> implements
    Function<P, T> {

  private final ValorFinanceiro dividendo;

  private final ValorFinanceiro divisor;

  public FuncaoDePonderacao(ValorFinanceiro dividendo, ValorFinanceiro divisor) {
    this.dividendo = dividendo;
    this.divisor = divisor;
  }

  public T apply(P ponderavel) {
    return ponderavel.ponderar(dividendo, divisor);
  }

}