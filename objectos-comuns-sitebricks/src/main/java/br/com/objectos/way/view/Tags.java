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

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class Tags {

  public static Set<String> extractTemplates(String html) {
    Document doc = Jsoup.parse(html);

    return ImmutableSet.<String> builder()
        .addAll(parseAttributeKey(doc, "data-way-async"))
        .addAll(parseAttributeKey(doc, "data-way-lazy"))
        .addAll(parseAttributeKey(doc, "data-way-template"))
        .addAll(parseMustachePartials(html))
        .build();
  }

  public static String appendContext(String html, String json) {

    StringBuilder script = new StringBuilder();
    script.append("<head><script type=\"text/javascript\">");
    script.append("var context = ");
    script.append(json);
    script.append(";");
    script.append("</script>");

    return html.replaceFirst("<head>", Matcher.quoteReplacement(script.toString()));
  }

  @VisibleForTesting
  static Iterable<String> parseAttributeKey(Document doc, String attributeKey) {
    Elements template = doc.getElementsByAttribute(attributeKey);
    return Iterables.transform(template, new ToAttrValue(attributeKey));
  }

  static Iterable<String> parseMustachePartials(String html) {
    Set<String> result = newHashSet();

    Pattern regex = Pattern.compile("\\{\\{>([^\\}]*)\\}\\}");
    Matcher matcher = regex.matcher(html);

    while (matcher.find()) {
      String group = matcher.group(1);
      result.add(group.trim());
    }

    return result;
  }

  private static class ToAttrValue implements Function<Element, String> {

    private final String key;

    public ToAttrValue(String key) {
      this.key = key;
    }

    @Override
    public String apply(Element input) {
      return input.attr(key);
    }

  }

}