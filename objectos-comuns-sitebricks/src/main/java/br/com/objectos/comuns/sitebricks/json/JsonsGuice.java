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
package br.com.objectos.comuns.sitebricks.json;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newLinkedHashMap;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import br.com.objectos.comuns.sitebricks.BaseUrl;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
class JsonsGuice implements Jsons {

  private final BaseUrl baseUrl;

  private final ObjectMapper mapper;

  @Inject
  public JsonsGuice(BaseUrl baseUrl, ObjectMapper mapper) {
    this.baseUrl = baseUrl;
    this.mapper = mapper;
  }

  @Override
  public JsonBuilder builder() {
    return new JsonBuilderImpl();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> asMap(String json) {
    try {

      return mapper.readValue(json, Map.class);

    } catch (JsonParseException e) {

      throw Throwables.propagate(e);

    } catch (JsonMappingException e) {

      throw Throwables.propagate(e);

    } catch (IOException e) {

      throw Throwables.propagate(e);

    }
  }

  private class JsonBuilderImpl implements JsonBuilder {

    private final Map<String, Object> context = newLinkedHashMap();

    @Override
    public JsonBuilder add(String key, Object pojo) {
      context.put(key, pojo);
      return this;
    }

    @Override
    public JsonBuilder addJson(String key, String json) {
      Map<String, Object> map = asMap(json);
      context.put(key, map);
      return this;
    }

    @Override
    public JsonBuilder addValue(Object pojo) {
      Map<String, Object> map = toMap(pojo);
      context.putAll(map);
      return this;
    }

    @Override
    public JsonBuilderTo to(String key) {
      return new JsonBuilderToImpl(this, key);
    }

    @Override
    public String toString() {
      return writeValueAsString(context);
    }

    Object getValue(String key) {
      return context.get(key);
    }

    JsonBuilderImpl replace(String key, Object pojo) {
      context.put(key, pojo);
      return this;
    }

  }

  private class JsonBuilderToImpl implements JsonBuilderTo {

    private final JsonBuilderImpl builder;
    private final String to;

    private final Map<String, Object> context = new LinkedHashMap<String, Object>();

    public JsonBuilderToImpl(JsonBuilderImpl builder, String to) {
      this.builder = builder;
      this.to = to;
    }

    @Override
    public JsonBuilder append(String key, Object pojo) {
      context.put(key, pojo);
      return done();
    }

    @Override
    public JsonBuilder appendForm(Class<? extends Annotation> method, String url, Object... objects) {
      Map<Object, Object> form = newLinkedHashMap();

      String suffix = String.format(url, objects);
      form.put("action", String.format("%s/%s", baseUrl.get(), suffix));
      form.put("method", method.getSimpleName().toLowerCase());
      context.put("form", form);

      return done();
    }

    @Override
    public JsonBuilder appendSearch(
        Class<? extends Annotation> method, String url, Object... objects) {

      Map<Object, Object> form = newLinkedHashMap();

      String suffix = String.format(url, objects);
      form.put("action", String.format("%s/%s", baseUrl.get(), suffix));
      form.put("method", method.getSimpleName().toLowerCase());
      context.put("search", form);

      return done();

    }

    @Override
    public JsonBuilder appendUrl(String key, String url, Object... objects) {
      String val = baseUrl.get();

      String suffix = String.format(url, objects);

      if (!Strings.isNullOrEmpty(suffix)) {
        val = String.format("%s/%s", baseUrl.get(), suffix);
      }

      context.put(key, val);
      return done();
    }

    @SuppressWarnings("unchecked")
    private JsonBuilder done() {
      Object val = builder.getValue(to);

      if (val == null) {
        val = context;
      } else if (val instanceof Map) {
        val = valMap(val);
      } else {
        Object object = toJsonObject(val);

        if (object instanceof List) {
          List<Map<String, Object>> mapArray = (List<Map<String, Object>>) object;

          List<Map<String, Object>> list = newArrayList();
          for (Map<String, Object> map : mapArray) {
            list.add(valMap(map));
          }

          val = list;
        } else {
          val = valMap(object);
        }
      }

      return builder.replace(to, val);
    }

    private Map<String, Object> valMap(Object val) {
      @SuppressWarnings("unchecked")
      Map<String, Object> map = (Map<String, Object>) val;
      map = newLinkedHashMap(map);
      map.putAll(context);
      return map;
    }

  }

  private Object toJsonObject(Object pojo) {
    try {

      String json = writeValueAsString(pojo);
      return mapper.readValue(json, Object.class);

    } catch (JsonParseException e) {

      throw Throwables.propagate(e);

    } catch (JsonMappingException e) {

      throw Throwables.propagate(e);

    } catch (IOException e) {

      throw Throwables.propagate(e);

    }
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> toMap(Object pojo) {
    try {

      String json = writeValueAsString(pojo);
      return mapper.readValue(json, Map.class);

    } catch (JsonParseException e) {

      throw Throwables.propagate(e);

    } catch (JsonMappingException e) {

      throw Throwables.propagate(e);

    } catch (IOException e) {

      throw Throwables.propagate(e);

    }
  }

  private String writeValueAsString(Object context) {
    try {

      return mapper.writeValueAsString(context);

    } catch (JsonGenerationException e) {

      throw Throwables.propagate(e);

    } catch (JsonMappingException e) {

      throw Throwables.propagate(e);

    } catch (IOException e) {

      throw Throwables.propagate(e);

    }
  }

}