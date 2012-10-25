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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TagsTest {

  public void extract_templates() throws IOException {
    Document doc = parse("/views/tags.html");

    Set<String> res = Tags.extractTemplates(doc);
    assertThat(res.size(), equalTo(3));
    assertTrue(res.contains("hd.mustache"));
    assertTrue(res.contains("bd.mustache"));
    assertTrue(res.contains("sub/render.mustache"));
  }

  public void extract_templates_empty() throws IOException {
    Document doc = parse("/views/tags-empty.html");

    Set<String> res = Tags.extractTemplates(doc);
    assertThat(res.size(), equalTo(0));
  }

  private Document parse(String resource) throws IOException {
    String html = read(resource);
    Document doc = Jsoup.parse(html);
    doc.outputSettings().prettyPrint(false);
    return doc;
  }

  private String read(String resource) throws IOException {
    URL url = Resources.getResource(getClass(), resource);
    return Resources.toString(url, Charsets.UTF_8);
  }

}