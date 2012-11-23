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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TesteDeCep {

  public void value_of_int() {
    Cep res = Cep.valueOf(4614013);

    assertThat(res.toString(), equalTo("04614-013"));
  }

  public void value_of_string() {
    Cep res = Cep.valueOf("04614-013");

    assertThat(res.toString(), equalTo("04614-013"));
  }

  public void value_of_string_sem_hifen() {
    Cep res = Cep.valueOf("04614013");

    assertThat(res.toString(), equalTo("04614-013"));
  }

  public void to_string() {
    Cep res = Cep.valueOf(4614013);

    assertThat(res.toString(), equalTo("04614-013"));
  }

  @DataProvider
  public Object[][] int_invalidos() {
    return new Object[][] {
      { 933887414 },
      { 268861386 },
      { 1143545257 } };
  }

  @Test(dataProvider = "int_invalidos", expectedExceptions = ExcecaoDeCepInvalido.class)
  public void value_of_int_invalido(int val) {
    Cep.valueOf(val);
  }

  public void equals_test() {
    EqualsVerifier
        .forClass(Cep.class)
        .verify();
  }

}