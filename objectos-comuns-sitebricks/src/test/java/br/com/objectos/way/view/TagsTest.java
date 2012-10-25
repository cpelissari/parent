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

import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

import br.com.objectos.comuns.sitebricks.json.Context;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TagsTest {

  private final ObjectMapper mapper = new ObjectMapper();

  public void extract_templates() throws IOException {
    String html = read("/views/tags.html");

    Set<String> res = Tags.extractTemplates(html);
    assertThat(res.size(), equalTo(3));
    assertTrue(res.contains("hd.mustache"));
    assertTrue(res.contains("bd.mustache"));
    assertTrue(res.contains("sub/render.mustache"));
  }

  public void extract_templates_empty() throws IOException {
    String html = read("/views/tags-empty.html");

    Set<String> res = Tags.extractTemplates(html);
    assertThat(res.size(), equalTo(0));
  }

  public void append_context() throws IOException {
    String before = read("/views/context-before.html");
    String after = read("/views/context-after.html");

    Context context = Context.of();
    context.put("msg", "Hello world!");
    String json = mapper.writeValueAsString(context);

    String res = Tags.appendContext(before, json);

    assertThat(res, equalTo(after));
  }

  private String read(String resource) throws IOException {
    URL url = Resources.getResource(getClass(), resource);
    return Resources.toString(url, Charsets.UTF_8);
  }

}