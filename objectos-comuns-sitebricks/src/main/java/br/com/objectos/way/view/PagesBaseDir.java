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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class PagesBaseDir {

  private static final String separator = System.getProperty("file.separator");

  private final String baseDir;

  private PagesBaseDir(String baseDir) {
    this.baseDir = baseDir;
  }

  public static PagesBaseDir atPackage(String pkgName) {
    String baseDir = pkgName.replaceAll("\\.", separator);
    return new PagesBaseDir(String.format("/%s/", baseDir));
  }

  String toRelative(Class<?> templateClass) {
    String className = templateClass.getName();
    String fileName = className.replace('.', '/');
    fileName = "/" + fileName;

    if (!fileName.startsWith(baseDir)) {
      throw new IllegalArgumentException("Template class not contained in baseDir: " + baseDir);
    }

    return fileName.substring(baseDir.length());
  }

  String getBaseDir() {
    return baseDir;
  }

}