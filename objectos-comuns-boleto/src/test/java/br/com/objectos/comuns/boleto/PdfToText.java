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
package br.com.objectos.comuns.boleto;

import java.io.IOException;

import com.itextpdf.text.pdf.PRTokeniser;
import com.itextpdf.text.pdf.PdfReader;

class PdfToText {

  public static String fromFile(String fileName) throws IOException {
    PdfReader contra = new PdfReader(fileName);
    byte[] contentContra = contra.getPageContent(1);
    PRTokeniser tokeniser = new PRTokeniser(contentContra);
    StringBuilder text1 = new StringBuilder();

    while (tokeniser.nextToken()) {
      if (tokeniser.getTokenType() == PRTokeniser.TokenType.STRING)
        text1.append(tokeniser.getStringValue());
    }

    return text1.toString();
  }

}