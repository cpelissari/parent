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
import java.net.URISyntaxException;

import org.testng.annotations.Test;

import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XComponent;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.CloseVetoException;
import com.sun.star.util.XCloseable;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
@Test
public class AccesRemoteServiceTest {

  public void should_connect_to_remote_service() throws IOException, IllegalArgumentException,
      CloseVetoException, java.io.IOException, URISyntaxException {
    File file = File.createTempFile("saida", ".odf");
    String output = file.toURI().toString();
    String texto = "###TEXTO INSERIDO NO TESTE###";

    Object oDesktop = ServiceFactoring.iniciarRemoto("localhost", 8100);

    XComponentLoader xCLoader = UnoRuntime.queryInterface(XComponentLoader.class, oDesktop);
    XComponent document = xCLoader.loadComponentFromURL("private:factory/swriter", "_blank", 0,
        new PropertyValue[0]);

    XTextDocument xTextDocument = UnoRuntime
        .queryInterface(XTextDocument.class, document);

    xTextDocument.getText()
        .getStart()
        .setString(texto);

    XStorable xStorable = UnoRuntime.queryInterface(XStorable.class, document);
    xStorable.storeToURL(output, new PropertyValue[0]);

    XCloseable xCloseable = UnoRuntime.queryInterface(XCloseable.class, document);
    xCloseable.close(true);

    File res = new File(output);
    assertThat(res.getName(), equalTo(file.getName()));
  }

}