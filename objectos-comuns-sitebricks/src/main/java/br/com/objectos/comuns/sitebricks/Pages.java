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

import java.util.List;

import br.com.objectos.comuns.relational.search.Page;
import br.com.objectos.comuns.relational.search.SimplePage;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Pages {

  private Pages() {
  }

  public static <T> List<T> limit(List<T> list, Page page) {
    page = page != null ? page : new SimplePage();

    int size = list.size();

    int firstIndex = page.getFirstIndex();
    firstIndex = firstIndex >= size ? size : firstIndex;

    int length = page.getLength();
    int lastIndex = firstIndex + length;
    lastIndex = lastIndex >= size ? size : lastIndex;

    return list.subList(firstIndex, lastIndex);
  }

}