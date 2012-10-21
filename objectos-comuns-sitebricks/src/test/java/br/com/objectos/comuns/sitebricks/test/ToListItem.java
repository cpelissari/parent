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
package br.com.objectos.comuns.sitebricks.test;

import static com.google.common.collect.Lists.transform;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.common.base.Supplier;
import com.google.sitebricks.client.WebResponse;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ToListItem implements Supplier<List<String>> {

  private final WebResponse response;

  public ToListItem(WebResponse response) {
    this.response = response;
  }

  @Override
  public List<String> get() {
    Document doc = Jsoup.parse(response.toString());
    Elements trs = doc.select("li");
    return transform(trs, new ToText());
  }

}