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
package br.com.objectos.comuns.uno.base;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.text.XText;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextRange;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XCloseable;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
@Test
public class TesteDeGerarDocumentoPdf {

  public void teste_uno_abrir_documento() throws BootstrapException, Exception, IOException {
    String texto1 = "###String no 001###";
    String texto2 = "###String no 002###";
    String entradaDoc = "src/test/resources/docWord972000Xp.doc";
    String saidaPdf = "file://src/test/resources/SAIDA.pdf";
    String contraPdf = "src/test/resources/CONTRA.pdf";

    // -- Prepara contexto

    XComponentContext localContext = Bootstrap.bootstrap();
    XMultiComponentFactory serviceManager = localContext.getServiceManager();

    Object oDesktop = serviceManager
        .createInstanceWithContext("com.sun.star.frame.Desktop", localContext);

    PropertyValue[] xValues = new PropertyValue[1];
    xValues[0] = new PropertyValue();
    xValues[0].Name = "Hidden";
    xValues[0].Value = true;

    // -- Carrega documento

    XComponentLoader xCLoader = UnoRuntime.queryInterface(XComponentLoader.class, oDesktop);
    XComponent document = xCLoader.loadComponentFromURL(appUri() + entradaDoc, "_blank", 0,
        xValues);

    // -- Insere String

    XTextDocument xTextDocument = UnoRuntime
        .queryInterface(XTextDocument.class, document);

    XText xText = xTextDocument.getText();
    XTextRange start = xText.getStart();
    XTextRange end = xText.getEnd();

    xText.insertString(start, texto1, false);
    xText.insertString(end, texto2, false);

    // -- Salvar documento em pdf

    XStorable xStorable = UnoRuntime.queryInterface(XStorable.class, document);
    PropertyValue[] storeProps = new PropertyValue[1];
    storeProps[0] = new PropertyValue();
    storeProps[0].Name = "FilterName";
    storeProps[0].Value = "writer_pdf_Export";
    xStorable.storeToURL(saidaPdf, storeProps);

    // -- Fecha documento

    XCloseable xCloseable = UnoRuntime.queryInterface(XCloseable.class, document);
    xCloseable.close(true);
    String res = PdfToString.fromFile(saidaPdf.substring(7));
    assertThat(PdfToString.fromFile(contraPdf), equalTo(res));

    File file = new File("src/test/resources/.~lock.SAIDA.pdf#");
    file.delete();

  }

  private String appUri() {
    return "file://" + new File("").getAbsolutePath() + "/";
  }

}