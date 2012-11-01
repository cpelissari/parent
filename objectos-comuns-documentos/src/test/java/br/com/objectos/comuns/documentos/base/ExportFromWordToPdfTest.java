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

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.XCloseable;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
@Test
public class ExportFromWordToPdfTest {

  public void open_document_and_save_in_pdf_format() throws BootstrapException,
      Exception, IOException {
    String entradaDoc = "src/test/resources/docWord972000Xp.doc";
    String tmpDir = System.getProperty("java.io.tmpdir");
    String saidaPdf = "file://" + tmpDir + "/output.pdf";
    String contraPdf = "src/test/resources/docWord972000Xp.pdf";

    Object oDesktop = ServiceFactoring.iniciarRemoto("localhost", 8100);

    PropertyValue[] xValues = new PropertyValue[1];
    xValues[0] = new PropertyValue();
    xValues[0].Name = "Hidden";
    xValues[0].Value = false;

    XComponentLoader xCLoader = UnoRuntime.queryInterface(XComponentLoader.class, oDesktop);
    XComponent document = xCLoader.loadComponentFromURL(appUri() + entradaDoc, "_blank", 0,
        xValues);

    XStorable xStorable = UnoRuntime.queryInterface(XStorable.class, document);
    PropertyValue[] storeProps = new PropertyValue[1];
    storeProps[0] = new PropertyValue();
    storeProps[0].Name = "FilterName";
    storeProps[0].Value = "writer_pdf_Export";
    xStorable.storeToURL(saidaPdf, storeProps);

    XCloseable xCloseable = UnoRuntime.queryInterface(XCloseable.class, document);
    xCloseable.close(true);
    String res = PdfToString.fromFile(saidaPdf.substring(7));
    assertThat(PdfToString.fromFile(contraPdf), equalTo(res));

  }
  private String appUri() {
    return "file://" + new File("").getAbsolutePath() + "/";
  }

}