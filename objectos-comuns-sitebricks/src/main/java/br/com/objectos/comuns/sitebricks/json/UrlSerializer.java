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

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import br.com.objectos.comuns.sitebricks.BaseUrl;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class UrlSerializer extends JsonSerializer<Url> {

  private final BaseUrl baseUrl;

  public UrlSerializer(BaseUrl baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Override
  public void serialize(Url value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {

    String url = value.toAbsolute(baseUrl);
    jgen.writeString(url);

  }

}