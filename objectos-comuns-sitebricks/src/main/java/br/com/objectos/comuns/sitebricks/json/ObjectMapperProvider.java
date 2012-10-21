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

import org.codehaus.jackson.map.ObjectMapper;

import br.com.objectos.comuns.sitebricks.BaseUrl;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.client.transport.JacksonJsonTransport;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ObjectMapperProvider implements Provider<ObjectMapper> {

  private final BaseUrl baseUrl;

  private final JacksonJsonTransport transport;

  @Inject
  public ObjectMapperProvider(BaseUrl baseUrl, JacksonJsonTransport transport) {
    this.baseUrl = baseUrl;
    this.transport = transport;
  }

  @Override
  public ObjectMapper get() {
    ObjectMapper mapper = transport.getObjectMapper();
    return new ObjectMapperDecorator(baseUrl).decorate(mapper);
  }

}