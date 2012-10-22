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
import static org.testng.Assert.assertTrue;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TesteDeCpf {

  @DataProvider
  public Object[][] validos() {
    return new Object[][] {
        { 79338874141l }, { 42688613863l }, { 11435452577l } };
  }

  @Test(dataProvider = "validos")
  public void is_valido(long val) {
    Cpf res = Cpf.valueOf(val);

    assertThat(res.isValido(), equalTo(true));
  }

  public void is_not_valido() {
    Cpf res = Cpf.valueOf(79338874142l);

    assertThat(res.isValido(), equalTo(false));
  }

  @DataProvider
  public Object[][] parser() {
    return new Object[][] {
        { "793.388.741-41" }, { "79338874141" }, { "793.388.741/41" }, { "793,388,741/41" } };
  }

  @Test(dataProvider = "parser")
  public void parse_valid(String val) {
    Cpf res = Cpf.parseCPF(val);

    assertThat(res.toString(), equalTo("793.388.741-41"));
  }

  @DataProvider
  public Object[][] parserInvalid() {
    return new Object[][] {
        { "abc" }, { "123" } };
  }

  @Test(dataProvider = "parserInvalid", expectedExceptions = ExcecaoDeCpfInvalido.class)
  public void parse_invalid(String val) {
    Cpf.parseCPF(val);

    assertTrue(false);
  }

  public void to_string() {
    Cpf res = Cpf.valueOf(79338874141l);

    assertThat(res.toString(), equalTo("793.388.741-41"));
  }

  public void equals_test() {
    EqualsVerifier
        .forClass(Cpf.class)
        .withRedefinedSuperclass()
        .verify();
  }

}