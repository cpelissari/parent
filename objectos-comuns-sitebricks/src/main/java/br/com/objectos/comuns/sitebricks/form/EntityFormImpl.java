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

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import br.com.objectos.comuns.relational.jdbc.Crud;
import br.com.objectos.comuns.sitebricks.BaseUrl;
import br.com.objectos.comuns.sitebricks.form.FormResponse.Error;
import br.com.objectos.comuns.sitebricks.json.EntityJson;

import com.google.inject.Provider;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class EntityFormImpl<T extends EntityJson> implements EntityForm<T> {

  private final Class<T> type;

  private final BaseUrl baseUrl;
  private final Crud crud;
  private final Provider<Request> requests;

  private ActionListener<T> listener = new EmptyActionListener<T>();
  private Redirect<T> redirect;

  public EntityFormImpl(Class<T> type, BaseUrl baseUrl, Crud crud, Provider<Request> requests) {
    this.type = type;
    this.baseUrl = baseUrl;
    this.crud = crud;
    this.requests = requests;
  }

  @Override
  public EntityForm<T> withActionListener(ActionListener<T> listener) {
    this.listener = listener;
    return this;
  }

  @Override
  public EntityForm<T> withRedirect(Redirect<T> redirect) {
    this.redirect = redirect;
    return this;
  }

  @Override
  public Reply<?> reply() {
    Request request = requests.get();
    T pojo = request.read(type).as(Json.class);

    List<Error> errors = newArrayList();

    try {
      tryToExecute(request, pojo);
    } catch (Throwable e) {
      errors.add(new ErrorImpl(null, e));
    }

    String redirectUrl = null;

    if (errors.isEmpty() && redirect != null) {
      redirectUrl = redirect.getUrl(baseUrl, pojo);
    }

    return Reply.with(new FormJson(errors, redirectUrl)).as(Json.class);
  }

  private void tryToExecute(Request request, T pojo) {
    switch (Method.parse(request)) {
    case POST:
      crud.create(pojo);
      listener.onCreate(pojo);
      break;

    case PUT:
      crud.update(pojo);
      listener.onUpdate(pojo);
      break;

    case DELETE:
      crud.delete(pojo);
      listener.onDelete(pojo);

      break;
    }
  }

  private enum Method {

    POST,
    PUT,
    DELETE;

    public static Method parse(Request request) {
      String method = request.method();
      return Method.valueOf(method.toUpperCase());
    }

  }

}