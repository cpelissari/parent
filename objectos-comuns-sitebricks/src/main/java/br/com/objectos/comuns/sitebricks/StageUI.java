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

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@JsonSerialize(using = StageUI.Serializer.class)
public enum StageUI {

  DEVELOPMENT,

  PRODUCTION;

  public static class Serializer extends JsonSerializer<StageUI> {

    @Override
    public void serialize(StageUI value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonProcessingException {

      jgen.writeStartObject();
      jgen.writeBooleanField("development", DEVELOPMENT.equals(value));
      jgen.writeBooleanField("production", PRODUCTION.equals(value));
      jgen.writeEndObject();

    }

  }

}