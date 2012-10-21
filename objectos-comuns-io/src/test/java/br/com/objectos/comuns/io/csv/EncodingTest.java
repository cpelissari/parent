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
package br.com.objectos.comuns.io.csv;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

import org.testng.annotations.Test;

import br.com.objectos.comuns.io.Encoding;
import br.com.objectos.comuns.io.Line;
import br.com.objectos.comuns.io.ParsedLines;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class EncodingTest {

  public void verifiqueUnicode() {
    SortedMap<String, Charset> availableCharsets = Charset.availableCharsets();
    System.out.print(availableCharsets.keySet());

    InputStream inputStream = obterStream("UTF_8.TXT");

    ParsedLines arquivo = CsvFile
        .parse(inputStream)
        .onSemicolons()
        .skipFirstLines(1)
        .getLines();

    Iterator<Line> iterator = arquivo.iterator();

    assertTrue(iterator.hasNext());

    Line linha = iterator.next();
    assertEquals(linha.column(1).get(String.class), "BR MALLS PARTICIPAÇOES S.A.");
  }

  public void verifiqueCp850() {
    InputStream inputStream = obterStream("CP_850.TXT");

    ParsedLines arquivo = CsvFile
        .parse(inputStream)
        .encodedWith(Encoding.CP_850)
        .onSemicolons()
        .skipFirstLines(1)
        .getLines();

    Iterator<Line> iterator = arquivo.iterator();

    assertTrue(iterator.hasNext());

    Line linha = iterator.next();
    assertEquals(linha.column(1).get(String.class), "BR MALLS PARTICIPAÇOES S.A.");
  }

  private InputStream obterStream(String nome) {
    ClassLoader classLoader = getClass().getClassLoader();

    return classLoader.getResourceAsStream("csv/" + nome);
  }

}