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
package br.com.objectos.comuns.sitebricks;

import java.util.Map;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class HeadersWrapper {

  private final Map<String, String> headers;

  public HeadersWrapper(Map<String, String> headers) {
    this.headers = headers;
  }

  public String param(String key) {
    return headers.get(key);
  }

  public long longParam(String param) {
    long res = 0;

    String id = headers.get(param);
    if (id != null) {
      try {
        res = Long.parseLong(id);
      } catch (NumberFormatException e) {

      }
    }

    return res;
  }

  public int integerParam(String param) {
    int res = 0;

    String id = headers.get(param);
    if (id != null) {
      try {
        res = Integer.parseInt(id);
      } catch (NumberFormatException e) {

      }
    }

    return res;
  }

}