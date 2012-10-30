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
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XCloseable;
import com.sun.star.util.XReplaceDescriptor;
import com.sun.star.util.XReplaceable;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
@Test
public class TesteDeSubstituirTagPorNome {

  public void abrir_documento_trocar_tags() throws BootstrapException, Exception, IOException {
    String entradaDoc = "src/test/resources/contratoComTags.doc";
    String saidaPdf = "file:///tmp/contratoComTagsRes.pdf";
    String contraPdf = "src/test/resources/CONTRA.pdf";

    String tagNome = "%NOME%";
    String tagRg = "%RG%";
    String nome = "JOSE DA SILVA";
    String rg = "253966863";

    XComponentContext localContext = Bootstrap.bootstrap();
    XMultiComponentFactory serviceManager = localContext.getServiceManager();

    Object oDesktop = serviceManager
        .createInstanceWithContext("com.sun.star.frame.Desktop", localContext);

    XComponent document = loadFile(appUri() + entradaDoc, oDesktop);

    XReplaceable xReplaceable = UnoRuntime.queryInterface(XReplaceable.class, document);
    XReplaceDescriptor replace1 = xReplaceable.createReplaceDescriptor();
    XReplaceDescriptor replace2 = xReplaceable.createReplaceDescriptor();

    replace1.setSearchString(tagNome);
    replace1.setReplaceString(nome);
    replace2.setSearchString(tagRg);
    replace2.setReplaceString(rg);

    xReplaceable.replaceAll(replace1);
    xReplaceable.replaceAll(replace2);

    XStorable xStorable = UnoRuntime.queryInterface(XStorable.class, document);
    PropertyValue[] storeProps = new PropertyValue[1];
    storeProps[0] = new PropertyValue();
    storeProps[0].Name = "FilterName";
    storeProps[0].Value = "writer_pdf_Export";
    xStorable.storeToURL(saidaPdf, storeProps);

    XCloseable xCloseable = UnoRuntime.queryInterface(XCloseable.class, document);
    xCloseable.close(true);

    String res = PdfToString.fromFile(saidaPdf);
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
    xValues[0].Value = true;

    XComponentLoader xCLoader = UnoRuntime.queryInterface(XComponentLoader.class, service);
    XComponent document = xCLoader.loadComponentFromURL(uri, "_blank", 0,
        xValues);

    return document;
  }

}