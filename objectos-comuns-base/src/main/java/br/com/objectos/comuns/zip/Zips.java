/*
 * Copyright 2011 Objectos, Fábrica de Software LTDA.
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
package br.com.objectos.comuns.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.common.io.ByteStreams;

/**
 * @author aline.heredia@objectos.com.br (Aline Heredia)
 */
public class Zips {

  private Zips() {
  }

  public static ZipDescompactado descompactar(ZipFile zip) {
    ZipDescompactado resultado = new ZipDescompactado();

    for (Enumeration<? extends ZipEntry> e = zip.entries(); e.hasMoreElements();) {
      ZipEntry entry = e.nextElement();

      try {
        InputStream input = zip.getInputStream(entry);

        File file = File.createTempFile("obj-comuns-zip", ".zip");
        OutputStream output = new FileOutputStream(file);

        ByteStreams.copy(input, output);

        resultado.adicionar(file);
      } catch (IOException ex) {
        resultado.adicionar(ex, entry.getName());
      }
    }

    return resultado;
  }

}