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
package br.com.objectos.comuns.io.xls;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.objectos.comuns.io.Line;
import br.com.objectos.comuns.io.ParsedLines;

/**
 * @author aline.heredia@objectos.com.br (Aline Heredia)
 */
@Test
public class XlsFileIteratorTest {

  private ParsedLines arquivo;

  @BeforeClass
  public void prepararClasse() throws IOException {
    XlsFile construtor = new MiniFakeXlsFile().get();
    arquivo = construtor.skipFirstLines(10).getLines();
  }

  public void verifiqueIterador() {
    Iterator<Line> iterator = arquivo.iterator();

    assertTrue(iterator.hasNext());
    assertNome(iterator.next(), "BANCO A.J. RENNER S.A.");

    assertTrue(iterator.hasNext());
    assertNome(iterator.next(), "BANCO ABC BRASIL S.A.");

    assertTrue(iterator.hasNext());
    assertNome(iterator.next(), "BANCO ALFA S.A.");

    assertTrue(iterator.hasNext());
    assertNome(iterator.next(), "BANCO ALVORADA S.A.");

    assertTrue(iterator.hasNext());
    assertNome(iterator.next(), "BANCO ARBI S.A.");

    assertFalse(iterator.hasNext());
  }

  public void verifiqueHasNext() {
    Iterator<Line> iterator = arquivo.iterator();

    assertTrue(iterator.hasNext());
    assertTrue(iterator.hasNext());
    assertTrue(iterator.hasNext());
    assertTrue(iterator.hasNext());
    assertNome(iterator.next(), "BANCO A.J. RENNER S.A.");

    assertTrue(iterator.hasNext());
    assertTrue(iterator.hasNext());
    assertNome(iterator.next(), "BANCO ABC BRASIL S.A.");

    assertTrue(iterator.hasNext());
    assertNome(iterator.next(), "BANCO ALFA S.A.");

    assertTrue(iterator.hasNext());
    assertNome(iterator.next(), "BANCO ALVORADA S.A.");

    assertTrue(iterator.hasNext());
    assertNome(iterator.next(), "BANCO ARBI S.A.");

    assertFalse(iterator.hasNext());
  }

  private void assertNome(Line linha, String esperado) {
    assertEquals(linha.column(1).get(String.class), esperado);
  }

}
