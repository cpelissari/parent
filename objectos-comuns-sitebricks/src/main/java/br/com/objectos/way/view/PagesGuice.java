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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import br.com.objectos.comuns.sitebricks.Html;
import br.com.objectos.comuns.sitebricks.Mimes;

import com.github.mustachejava.Mustache;
import com.google.common.base.Throwables;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
class PagesGuice extends Pages {

  private final Mustaches mustaches;

  private final ObjectMapper objectMapper;

  private final Views views;

  @Inject
  public PagesGuice(Mustaches mustaches, ObjectMapper objectMapper, Views views) {
    this.mustaches = mustaches;
    this.objectMapper = objectMapper;
    this.views = views;
  }

  @Override
  public Reply<?> get(Class<?> templateClass, Context context) {
    Response response = new Response(templateClass, context);
    return Reply.with(response.html).as(Html.class);
  }

  @Override
  public Reply<?> post(Class<?> templateClass, Context context) {
    Response response = new Response(templateClass, context);
    return Reply.with(response.context).as(Json.class).type(Mimes.APPLICATION_JSON_UTF8);
  }

  private class Response {

    final String html;
    final String json;
    final Context context;

    public Response(Class<?> templateClass, Context context) {
      String html = render(templateClass, context);

      Set<String> viewSet = Tags.extractTemplates(html);
      views.populate(context, viewSet);

      this.json = toJson(context);
      this.html = Tags.appendContext(html, json);
      this.context = context;
    }

  }

  private String render(Class<?> templateClass, Context context) {
    try {

      Mustache mustache = mustaches.compile(templateClass);

      StringWriter writer = new StringWriter();
      Map<?, ?> scope = toMustacheScope(context);

      Writer execute = mustache.execute(writer, scope);
      execute.flush();

      return writer.toString();

    } catch (IOException e) {

      throw Throwables.propagate(e);

    }
  }

  private String toJson(Context context) {
    try {

      return objectMapper.writeValueAsString(context);

    } catch (JsonGenerationException e) {

      throw Throwables.propagate(e);

    } catch (JsonMappingException e) {

      throw Throwables.propagate(e);

    } catch (IOException e) {

      throw Throwables.propagate(e);

    }
  }

  private Map<?, ?> toMustacheScope(Context context) {
    try {

      String json = toJson(context);
      return objectMapper.readValue(json, Map.class);

    } catch (JsonGenerationException e) {

      throw Throwables.propagate(e);

    } catch (JsonMappingException e) {

      throw Throwables.propagate(e);

    } catch (IOException e) {

      throw Throwables.propagate(e);

    }
  }

}