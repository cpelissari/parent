/*
 * Copyright 2011 Objectos, Fábrica de Software LTDA.
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

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public interface FormResponse {

  interface Error {

    @JsonProperty
    String getName(); // as in form name

    @JsonProperty
    String getMessage();

    @JsonProperty
    String getMessageBody();

  }

  @JsonProperty
  List<Error> getErrors();

  @JsonProperty
  String getRedirectUrl();

  @JsonProperty
  boolean isValid();

}