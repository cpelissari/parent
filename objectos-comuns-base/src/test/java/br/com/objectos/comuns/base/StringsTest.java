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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class StringsTest {

  @DataProvider(name = "alfa")
  public Object[][] alfaProvider() {
    return new Object[][] {
        {
            "Norm7al",
            "Norm7al" },
        {
            "Dois2 Normais",
            "Dois2 Normais" },
        {
            "Esp%al",
            "Espal" },
        {
            "Doi* Es()8",
            "Doi Es8" } };
  }

  @Test(dataProvider = "alfa")
  public void verifiqueAlfanumerico(String prova, String esperado) {
    String alfnumerico = Strings.alphanum(prova).toString();
    assert alfnumerico.equals(esperado) : alfnumerico;
  }

  @DataProvider(name = "camelCase")
  public Object[][] camelCaseProvider() {
    return new Object[][] {
        {
            "SimPLES",
            "Simples" },
        {
            "simples",
            "Simples" },
        {
            "duas palavras",
            "DuasPalavras" },
        {
            "Duas Palavras",
            "DuasPalavras" },
        {
            "Dois esp&*ais",
            "DoisEsp&*ais" } };
  }

  @Test(dataProvider = "camelCase")
  public void verifiqueCamelCase(String prova, String esperado) {
    String camelCase = Strings.camelCase(prova).toString();
    assert camelCase.equals(esperado) : camelCase;
  }

  @Test
  public void verifiqueRemocaoDeAcentos() {
    String semAcentos = Strings.accentsToAscii(
        "È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô,ç").toString();
    assert semAcentos.equals("E,E,E,E,U,U,I,I,A,A,O,e,e,e,e,u,u,i,i,a,a,o,c") : semAcentos;
  }

  @DataProvider(name = "espacos")
  public Object[][] espacosProvider() {
    return new Object[][] {
        {
            "um espaco",
            "umespaco" },
        {
            "do is espacos",
            "doisespacos" },
        {
            " no comeco",
            "nocomeco" },
        {
            "no final ",
            "nofinal" },
        {
            "um\ttab",
            "umtab" } };
  }

  @Test(dataProvider = "espacos")
  public void verifiqueRemocaoDeEspacos(String prova, String esperado) {
    String semEspacos = Strings.stripWhitespace(prova).toString();
    assert semEspacos.equals(esperado) : semEspacos;
  }

  @DataProvider(name = "whitespace2char")
  public Object[][] whitespace2charProvider() {
    return new Object[][] {
        {
            "um espaco",
            "um-espaco" },
        {
            "do is espacos",
            "do-is-espacos" },
        {
            " no comeco",
            "-no-comeco" },
        {
            "no final ",
            "no-final-" },
        {
            "um\ttab",
            "um-tab" } };
  }

  @Test(dataProvider = "whitespace2char")
  public void white_space_to_char(String string, String expected) {
    String res = Strings.whitespaceTo(string, "-").toString();
    assert res.equals(expected) : res;
  }

}