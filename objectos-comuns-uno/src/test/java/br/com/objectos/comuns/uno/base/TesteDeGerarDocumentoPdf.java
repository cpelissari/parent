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

import org.testng.annotations.Test;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
@Test
public class TesteDeGerarDocumentoPdf {

  public void teste_uno_abrir_documento() throws BootstrapException, Exception {

    XComponentContext context = Bootstrap.bootstrap();
    XMultiComponentFactory serviceManager = context.getServiceManager();

    Object oDesktop = serviceManager.createInstanceWithContext("com.sun.star.frame.Desktop",
        context);

    XComponentLoader xCLoader = (XComponentLoader) UnoRuntime.queryInterface(
        XComponentLoader.class, oDesktop);

    XComponent document = xCLoader.loadComponentFromURL("private:factory/swriter", "_blank", 0,
        new PropertyValue[0]);

    String caminho = "src/test/resources/Arquivo.odt";

    XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);

    PropertyValue[] store = new PropertyValue[0];
    xStorable.storeAsURL(caminho, store);

    store = new PropertyValue[1];
    store[0] = new PropertyValue();
    store[0].Name = "FilterName";
    store[0].Value = "writer_pdf_Export";

    xStorable.storeToURL("src/test/resources/Arquivo.pdf", store);
  }

}