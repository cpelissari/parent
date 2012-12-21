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
import static com.google.common.collect.Maps.newHashMap;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import br.com.objectos.comuns.relational.jdbc.Crud;
import br.com.objectos.comuns.sitebricks.BaseUrl;
import br.com.objectos.comuns.sitebricks.form.FormResponse.Error;
import br.com.objectos.comuns.sitebricks.json.EntityJson;
import br.com.objectos.way.view.Context;

import com.google.common.base.Preconditions;
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

  private MethodFilter<T> filter = new AlwaysMethodFilter<T>();
  private ActionListener<T> listener = new EmptyActionListener<T>();
  private ContextDecorator<T> decorator;
  private Redirect<T> redirect;

  private final Map<Method, Action<T>> actionMap;
  private final Map<Method, Redirect<T>> redirectMap = newHashMap();

  public EntityFormImpl(Class<T> type, BaseUrl baseUrl, Crud crud, Provider<Request> requests) {
    this.type = type;
    this.baseUrl = baseUrl;
    this.crud = crud;
    this.requests = requests;

    actionMap = newHashMap();
    actionMap.put(Method.POST, new DefaultCreateAction());
    actionMap.put(Method.PUT, new DefaultUpdateAction());
    actionMap.put(Method.DELETE, new DefaultDeleteAction());
  }

  @Override
  public EntityForm<T> withMethodFilter(MethodFilter<T> filter) {
    this.filter = filter;
    return this;
  }

  @Override
  public EntityForm<T> withActionListener(ActionListener<T> listener) {
    this.listener = listener;
    return this;
  }

  @Override
  public EntityForm<T> withContextDecorator(ContextDecorator<T> decorator) {
    Preconditions.checkArgument(redirect == null,
        "You cannot use a decorator and a redirect at the same time");
    this.decorator = decorator;
    return this;
  }

  @Override
  public EntityForm<T> withRedirect(Redirect<T> redirect) {
    Preconditions.checkArgument(decorator == null,
        "You cannot use a decorator and a redirect at the same time");
    this.redirect = redirect;
    return this;
  }

  @Override
  public OnMethod<T> on(final Class<? extends Annotation> methodAnnotation) {
    Preconditions.checkArgument(decorator == null,
        "You cannot use a decorator and a redirect at the same time");

    return new OnMethod<T>() {
      @Override
      public EntityForm<T> execute(Action<T> action) {
        Method method = Method.parse(methodAnnotation);
        actionMap.put(method, action);
        return EntityFormImpl.this;
      }

      @Override
      public EntityForm<T> redirect(Redirect<T> redirect) {
        Method method = Method.parse(methodAnnotation);
        redirectMap.put(method, redirect);
        return EntityFormImpl.this;
      }
    };
  }

  @Override
  public Reply<?> reply() {
    Request request = requests.get();
    T pojo = request.read(type).as(Json.class);

    Method method = Method.parse(request);

    List<Error> errors = newArrayList();

    try {
      tryToExecute(method, pojo);
    } catch (Throwable e) {
      errors.add(new ErrorImpl(null, e));
    }

    Context context = null;
    String redirectUrl = null;

    if (errors.isEmpty()) {
      if (decorator != null) {
        context = Context.of();
        decorator.decorate(context, pojo);
      }

      if (redirect != null) {
        redirectUrl = redirect.getUrl(baseUrl, pojo);
      }

      if (redirectMap.containsKey(method)) {
        Redirect<T> methodRedirect = redirectMap.get(method);
        redirectUrl = methodRedirect.getUrl(baseUrl, pojo);
      }
    }

    FormJson json = new FormJson(context, errors, redirectUrl);
    return Reply.with(json).as(Json.class);
  }

  private void tryToExecute(Method method, T pojo) {
    switch (method) {
    case POST:
      if (filter.shouldCreate(pojo)) {
        pojo = actionMap.get(method).execute(pojo);
        listener.onCreate(pojo);
      }
      break;

    case PUT:
      if (filter.shouldUpdate(pojo)) {
        pojo = actionMap.get(method).execute(pojo);
        listener.onUpdate(pojo);
      }
      break;

    case DELETE:
      if (filter.shouldDelete(pojo)) {
        pojo = actionMap.get(method).execute(pojo);
        listener.onDelete(pojo);
      }

      break;
    }
  }

  private static class AlwaysMethodFilter<T extends EntityJson> extends AbstractMethodFilter<T> {}

  private class DefaultCreateAction extends AbstractAction<T> {
    @Override
    public T execute(T pojo) {
      crud.create(pojo);
      return pojo;
    }
  }
  private class DefaultUpdateAction extends AbstractAction<T> {
    @Override
    public T execute(T pojo) {
      crud.update(pojo);
      return pojo;
    }
  }
  private class DefaultDeleteAction extends AbstractAction<T> {
    @Override
    public T execute(T pojo) {
      crud.delete(pojo);
      return pojo;
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

    public static Method parse(Class<? extends Annotation> annotation) {
      String method = annotation.getSimpleName();
      return Method.valueOf(method.toUpperCase());
    }

  }

}