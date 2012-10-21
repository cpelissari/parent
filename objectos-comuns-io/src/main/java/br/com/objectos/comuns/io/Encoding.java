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
package br.com.objectos.comuns.io;

import java.nio.charset.Charset;
import java.util.SortedMap;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public enum Encoding {

  UTF_8,

  ISO_8859_1,

  ISO_8859_5,

  ISO_8859_15,

  CP_850;

  public Charset toCharset() {
    switch (this) {
    case UTF_8:
      return Charset.forName("UTF-8");
    case ISO_8859_1:
      return Charset.forName("ISO-8859-1");
    case ISO_8859_5:
      return Charset.forName("ISO-8859-5");
    case ISO_8859_15:
      return Charset.forName("ISO-8859-15");
    case CP_850:
      SortedMap<String, Charset> charsets = Charset.availableCharsets();
      for (String nome : charsets.keySet()) {
        if (nome.contains("850")) {
          return Charset.forName(nome);
        }
      }
    default:
      throw new IllegalStateException("Charset " + name()
          + " não implementado ou não disponível neste sistema.");
    }
  }

}