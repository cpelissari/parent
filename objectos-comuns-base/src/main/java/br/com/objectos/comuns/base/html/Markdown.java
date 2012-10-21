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
package br.com.objectos.comuns.base.html;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Markdown {

  private static final String LINE_SEPARATOR = System.getProperty("line.separator");

  private final StringBuilder buffer = new StringBuilder();

  public Markdown br() {
    buffer.append(LINE_SEPARATOR);
    buffer.append(LINE_SEPARATOR);
    return this;
  }

  public MarkdownLinkBuilder link(String text) {
    return new LinkBuilderImpl(text);
  }
  public MarkdownLinkBuilder link(String template, Object... args) {
    String text = String.format(template, args);
    return new LinkBuilderImpl(text);
  }

  public Markdown text(Object text) {
    buffer.append(text.toString());
    return this;
  }
  public Markdown text(String template, Object... args) {
    String text = String.format(template, args);
    return text(text);
  }

  @Override
  public String toString() {
    return buffer.toString();
  }

  private class LinkBuilderImpl implements MarkdownLinkBuilder {

    private final String text;

    public LinkBuilderImpl(String text) {
      this.text = text;
    }

    @Override
    public Markdown toUrl(String template, Object... args) {
      String url = String.format(template, args);
      return toUrl(url);
    }

    @Override
    public Markdown toUrl(String url) {
      String md = String.format("[%s](%s)", text, url);
      buffer.append(md);
      return Markdown.this;
    }

  }

}