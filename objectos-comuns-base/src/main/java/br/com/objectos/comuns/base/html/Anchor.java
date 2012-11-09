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

import com.google.common.base.Strings;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Anchor {

  private String id;

  private String href;

  private String styleClass;

  private String target;

  private String html;

  @Override
  public String toString() {
    StringBuilder a = new StringBuilder();
    a.append("<a");

    if (!Strings.isNullOrEmpty(id)) {
      a.append(String.format(" id=\"%s\"", id));
    }

    if (!Strings.isNullOrEmpty(href)) {
      a.append(String.format(" href=\"%s\"", href));
    }

    if (!Strings.isNullOrEmpty(styleClass)) {
      a.append(String.format(" class=\"%s\"", styleClass));
    }

    if (!Strings.isNullOrEmpty(target)) {
      a.append(String.format(" target=\"%s\"", target));
    }

    a.append(">");

    if (!Strings.isNullOrEmpty(html)) {
      a.append(html);
    }

    a.append("</a>");
    return a.toString();
  }

  public Anchor id(String id) {
    this.id = id;
    return this;
  }

  public Anchor href(String href) {
    this.href = href;
    return this;
  }
  public Anchor href(String template, Object... args) {
    this.href = String.format(template, args);
    return this;
  }

  public Anchor target(String target) {
    this.target = target;
    return this;
  }

  public Anchor styleClass(String styleClass) {
    this.styleClass = styleClass;
    return this;
  }

  public Anchor html(String html) {
    this.html = html;
    return this;
  }
  public Anchor html(String template, Object... args) {
    this.html = String.format(template, args);
    return this;
  }

}