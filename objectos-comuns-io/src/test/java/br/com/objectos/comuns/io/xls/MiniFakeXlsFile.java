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
package br.com.objectos.comuns.io.xls;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * @author aline.heredia@objectos.com.br (Aline Heredia)
 */
public class MiniFakeXlsFile {

  public XlsFile get() throws IOException {

    InputStream inputStream = abrirArquivoDeTestes();

    return XlsFile.parse(inputStream);
  }

  private InputStream abrirArquivoDeTestes() throws IOException {
    InputStream is = getResourceAsStream("xls/201008BANCOS.xls.gz");

    GZIPInputStream gzip = new GZIPInputStream(is);

    return gzip;
  }

  private InputStream getResourceAsStream(String nome) {
    return getClass().getClassLoader().getResourceAsStream(nome);
  }

}