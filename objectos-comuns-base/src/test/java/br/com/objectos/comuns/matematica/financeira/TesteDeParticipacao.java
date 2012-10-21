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

import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;

import org.testng.annotations.Test;

/**
 * @author lenin.abe@objectos.com.br (Lenin Abe)
 */
@Test
public class TesteDeParticipacao {

  @Test(expectedExceptions = { NumberFormatException.class })
  public void verifiqueConstrutorForaDeEscalaFalhou() {

    BigDecimal bigDecimal = obterBigDecimal("21.900");

    new ParticipacaoImpl(bigDecimal);

  }

  public void verifiqueGetParticipacao() {

    Participacao participacao = obterParticipacao("0.1575");

    BigDecimal bigDecimal = obterBigDecimal("0.1575");

    assertEquals(participacao.getParticipacao(), bigDecimal);

  }

  private BigDecimal obterBigDecimal(String valor) {

    return new BigDecimal(valor);

  }

  private Participacao obterParticipacao(String valor) {

    BigDecimal val = new BigDecimal(valor);

    return new ParticipacaoImpl(val);

  }

}
