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

import br.com.objectos.comuns.relational.jdbc.Crud;
import br.com.objectos.comuns.sitebricks.BaseUrl;
import br.com.objectos.comuns.sitebricks.json.EntityJson;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.headless.Request;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class FormsGuice implements Forms {

  private final BaseUrl baseUrl;
  private final Crud crud;
  private final Provider<Request> requests;

  private final Validator validator;

  @Inject
  public FormsGuice(BaseUrl baseUrl, Crud crud, Provider<Request> requests, Validator validator) {
    this.baseUrl = baseUrl;
    this.crud = crud;
    this.requests = requests;
    this.validator = validator;
  }

  @Override
  public <T extends EntityJson> EntityForm<T> of(Class<T> type) {
    return new EntityFormImpl<T>(type, baseUrl, crud, requests);
  }

  @Override
  public <T> Form<T> newFormFor(T pojo) {
    return new FormImpl<T>(pojo, validator);
  }

}