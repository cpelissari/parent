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
package br.com.objectos.comuns.sitebricks.form;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@JsonSerialize(using = FormJson.Serializer.class)
class FormJson implements FormResponse {

  private final List<Error> errors;

  private final String redirectUrl;

  public FormJson(List<Error> errors, String redirectUrl) {
    this.errors = errors;
    this.redirectUrl = redirectUrl;
  }

  @Override
  public List<Error> getErrors() {
    return errors;
  }

  @Override
  public String getRedirectUrl() {
    return redirectUrl;
  }

  @Override
  public boolean isValid() {
    return errors.isEmpty();
  }

  public static class Serializer extends JsonSerializer<FormResponse> {

    @Override
    public void serialize(FormResponse value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonProcessingException {

      jgen.writeStartObject();
      jgen.writeObjectField("errors", value.getErrors());
      jgen.writeObjectField("valid", value.isValid());
      jgen.writeObjectField("redirectUrl", value.getRedirectUrl());
      jgen.writeEndObject();

    }

  }

}