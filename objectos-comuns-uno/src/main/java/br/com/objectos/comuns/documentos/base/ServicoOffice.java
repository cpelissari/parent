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

import com.sun.star.beans.XPropertySet;
import com.sun.star.bridge.UnoUrlResolver;
import com.sun.star.bridge.XUnoUrlResolver;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
class ServicoOffice {

  private static XComponentContext xComponentContext;
  private static XMultiComponentFactory xMultiComponentFactory;

  public static Object iniciarRemoto(String host, int port) {
    try {
      xComponentContext = Bootstrap.createInitialComponentContext(null);

      XUnoUrlResolver urlResolver = UnoUrlResolver.create(xComponentContext);

      Object initObject = urlResolver.resolve("uno:socket,host=" + host + ",port=" + port
          + ";urp;StarOffice.ServiceManager");
      xMultiComponentFactory = UnoRuntime.queryInterface(XMultiComponentFactory.class, initObject);
      XPropertySet xPropertySet = UnoRuntime.queryInterface(XPropertySet.class,
          xMultiComponentFactory);

      Object defaultContext = xPropertySet.getPropertyValue("DefaultContext");

      XComponentContext officeContext = UnoRuntime.queryInterface(XComponentContext.class,
          defaultContext);

      return xMultiComponentFactory.createInstanceWithContext(
          "com.sun.star.frame.Desktop", officeContext);

    } catch (Exception e) {
      e.printStackTrace();
    } catch (java.lang.Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public static Object iniciarLocal() {
    try {
      xComponentContext = Bootstrap.bootstrap();
      xMultiComponentFactory = xComponentContext.getServiceManager();

      return xMultiComponentFactory.createInstanceWithContext(
          "com.sun.star.frame.Desktop", xComponentContext);

    } catch (BootstrapException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

}