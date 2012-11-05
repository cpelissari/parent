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

import java.io.ByteArrayInputStream;
import java.io.RandomAccessFile;

import org.testng.annotations.Test;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.lang.XComponent;
import com.sun.star.lib.uno.adapter.InputStreamToXInputStreamAdapter;
import com.sun.star.uno.UnoRuntime;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
@Test
public class LoadDocumentFromByteArray {

  private RandomAccessFile file;

  public void test_bootstrap_local_uno_instalation() throws BootstrapException, Exception {
    String inputDoc = "src/test/resources/docWord972000Xp.doc";
    file = new RandomAccessFile(inputDoc, "r");
    byte[] documentByteArray = new byte[(int) file.length()];
    file.read(documentByteArray);

    Object oDesktop = ServiceFactoring.iniciarLocal();

    ByteArrayInputStream inputStream = new ByteArrayInputStream(documentByteArray);
    InputStreamToXInputStreamAdapter adapter = new InputStreamToXInputStreamAdapter(inputStream);

    PropertyValue[] loadProperties = new PropertyValue[1];
    loadProperties[0] = new PropertyValue();
    loadProperties[0].Name = "InputStream";
    loadProperties[0].Handle = -1;
    loadProperties[0].Value = adapter;

    XComponentLoader xCLoader =
        UnoRuntime.queryInterface(XComponentLoader.class, oDesktop);
    XComponent document = xCLoader.loadComponentFromURL("private:stream", "_blank", 0,
        loadProperties);

    xCLoader.loadComponentFromURL("private:factory/swriter", "_blank", 0,
        new PropertyValue[0]);

  }
}