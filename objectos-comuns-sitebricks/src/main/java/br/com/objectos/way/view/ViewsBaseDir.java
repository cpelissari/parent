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
package br.com.objectos.way.view;

import java.io.IOException;
import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ViewsBaseDir {

  private static final String separator = System.getProperty("file.separator");

  private final String baseDir;

  private ViewsBaseDir(String baseDir) {
    this.baseDir = baseDir;
  }

  public static ViewsBaseDir atPackage(String pkgName) {
    String baseDir = pkgName.replaceAll("\\.", separator);
    return new ViewsBaseDir(String.format("/%s/", baseDir));
  }

  public String get(String view) {
    try {
      String viewName = baseDir + view;
      ClassLoader ccl = Thread.currentThread().getContextClassLoader();
      URL url = ccl.getResource(viewName);

      if (url == null) {
        url = Resources.getResource(getClass(), viewName);
      }

      return Resources.toString(url, Charsets.UTF_8);
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not load view at " + view);
    }
  }

}