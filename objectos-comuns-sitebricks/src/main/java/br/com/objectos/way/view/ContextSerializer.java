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

import static com.google.common.collect.Maps.newLinkedHashMap;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;

import com.google.common.base.Throwables;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ContextSerializer extends JsonSerializer<Context> {

  private final ObjectMapper mapper;

  public ContextSerializer(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void serialize(Context value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {

    Map<Object, Object> toSerialize = newLinkedHashMap();

    Object root = value.root;
    if (root != null) {
      Map<String, Object> rootMap = toMap(root);
      toSerialize.putAll(rootMap);
    }

    Map<String, Object> map = value.map;
    toSerialize.putAll(map);

    jgen.writeObject(toSerialize);

  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> toMap(Object pojo) throws IOException {
    try {

      String json = writeValueAsString(pojo);
      return mapper.readValue(json, Map.class);

    } catch (JsonParseException e) {

      throw Throwables.propagate(e);

    } catch (JsonMappingException e) {

      throw Throwables.propagate(e);

    }
  }

  private String writeValueAsString(Object context) throws IOException {
    try {

      return mapper.writeValueAsString(context);

    } catch (JsonGenerationException e) {

      throw Throwables.propagate(e);

    } catch (JsonMappingException e) {

      throw Throwables.propagate(e);

    }
  }

}