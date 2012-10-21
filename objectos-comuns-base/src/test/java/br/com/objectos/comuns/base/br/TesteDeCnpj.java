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
package br.com.objectos.comuns.base.br;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TesteDeCnpj {

  public void verifiqueConstrucaoViaLong() {
    long objectos = 7430629000110l;

    Cnpj cnpj = Cnpj.valueOf(objectos);

    assertTrue(cnpj.isValido());
    assertEquals(cnpj.toString(), "07.430.629/0001-10");
  }

  public void verifiqueConstrucaoViaInscricaoFiliais() {
    int objectos = 7430629;
    int filiais = 1;

    Cnpj cnpj = Cnpj.valueOf(objectos, filiais);

    assertTrue(cnpj.isValido());
    assertEquals(cnpj.toString(), "07.430.629/0001-10");
  }

  @Test(expectedExceptions = ExcecaoDeCnpjInvalido.class)
  public void verifiqueErroDeConstrucaoViaLong() {
    long objectos = 7430629000111l;

    Cnpj cnpj = Cnpj.valueOf(objectos);

    assertFalse(cnpj.isValido());
  }

  public void verifiqueConstrucaoViaString() {
    String objectos = "07.430.629/0001-10";

    Cnpj cnpj = Cnpj.valueOf(objectos);

    assertTrue(cnpj.isValido());
  }

  @Test(expectedExceptions = ExcecaoDeCnpjInvalido.class)
  public void verifiqueErroDeConstrucaoViaString() {
    String objectos = "Estudar um pouco, depois mais um pouco...";

    Cnpj cnpj = Cnpj.valueOf(objectos);

    assertFalse(cnpj.isValido());
  }

}