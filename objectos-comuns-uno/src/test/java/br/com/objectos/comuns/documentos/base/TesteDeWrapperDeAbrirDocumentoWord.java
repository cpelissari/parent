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
package br.com.objectos.comuns.documentos.base;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.Test;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
@Test
public class TesteDeWrapperDeAbrirDocumentoWord {

  public void deve_abrir_documento_word_e_salvar_em_odt() throws IOException {
    String prova = "src/test/resources/docWord972000Xp.pdf";
    String origem = "src/test/resources/docWord972000Xp.doc";
    String destino = "/tmp/resultado.pdf";

    Documento documento = new DocumentoImpl();
    documento.toPdf();

    String contra = PdfToString.fromFile(prova);
    String res = PdfToString.fromFile(destino);
    assertThat(res, equalTo(contra));
  }

}