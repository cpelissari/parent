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

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

import br.com.objectos.comuns.documents.base.ServiceFactoring;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.XCloseable;
import com.sun.star.util.XReplaceDescriptor;
import com.sun.star.util.XReplaceable;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
@Test
public class ReplaceTagAndSaveTest {

  public void should_change_tag_by_names_in_document() throws BootstrapException, Exception,
      IOException {
    String inputDoc = "src/test/resources/fileWithTags.doc";
    String contraPdf = "src/test/resources/fileWithTags.pdf";
    String tmpDir = System.getProperty("java.io.tmpdir");
    String outputPdf = "file://" + tmpDir + "/fileWithTagsResult.pdf";

    String tagName = "%NOME%";
    String tagRg = "%RG%";
    String name = "JOSE DA SILVA";
    String rg = "s253966863";

    Object oDesktop = ServiceFactoring.iniciarRemoto("localhost", 8100);

    XComponent document = loadFile(appUri() + inputDoc, oDesktop);

    XReplaceable xReplaceable = UnoRuntime.queryInterface(XReplaceable.class, document);
    XReplaceDescriptor replace1 = xReplaceable.createReplaceDescriptor();
    XReplaceDescriptor replace2 = xReplaceable.createReplaceDescriptor();

    replace1.setSearchString(tagName);
    replace1.setReplaceString(name);
    replace2.setSearchString(tagRg);
    replace2.setReplaceString(rg);

    xReplaceable.replaceAll(replace1);
    xReplaceable.replaceAll(replace2);

    XStorable xStorable = UnoRuntime.queryInterface(XStorable.class, document);
    PropertyValue[] storeProps = new PropertyValue[1];
    storeProps[0] = new PropertyValue();
    storeProps[0].Name = "FilterName";
    storeProps[0].Value = "writer_pdf_Export";
    xStorable.storeToURL(outputPdf, storeProps);

    XCloseable xCloseable = UnoRuntime.queryInterface(XCloseable.class, document);
    xCloseable.close(true);

    String res = PdfToString.fromFile(outputPdf);
    String prova = PdfToString.fromFile(contraPdf);

    assertThat(res, equalTo(prova));
  }

  private String appUri() {
    return "file://" + new File("").getAbsolutePath() + "/";
  }

  private XComponent loadFile(String uri, Object service) throws com.sun.star.io.IOException,
      IllegalArgumentException {
    PropertyValue[] xValues = new PropertyValue[1];
    xValues[0] = new PropertyValue();
    xValues[0].Name = "Hidden";
    xValues[0].Value = false;

    XComponentLoader xCLoader = UnoRuntime.queryInterface(XComponentLoader.class, service);
    XComponent document = xCLoader.loadComponentFromURL(uri, "_blank", 0,
        xValues);

    return document;
  }

}