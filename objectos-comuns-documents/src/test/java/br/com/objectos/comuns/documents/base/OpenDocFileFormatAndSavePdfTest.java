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
package br.com.objectos.comuns.documents.base;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.Test;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
@Test
public class OpenDocFileFormatAndSavePdfTest {

  public void should_open_file_and_save_in_pdf_format() throws IOException {
    String origem = "src/test/resources/docWord972000Xp.doc";
    String tmpDir = System.getProperty("java.io.tmpdir");
    String destino = tmpDir + "/resultado.pdf";

    String contra = PdfToString.fromFile(origem);
    String res = PdfToString.fromFile(destino);
    assertThat(res, equalTo(contra));
  }

}