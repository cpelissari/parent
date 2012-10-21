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
import java.util.Set;

import javax.validation.ConstraintViolation;

import br.com.objectos.comuns.sitebricks.form.Form.Action;
import br.com.objectos.comuns.sitebricks.form.Form.Redirect;
import br.com.objectos.way.view.Context;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class FormResponseImpl<T> {

  private final Action<T> action;

  private final Redirect<T> redirect;

  private final Validator validator;

  private final T pojo;

  private final List<FormResponse.Error> errors;

  private String redirectUrl;

  public FormResponseImpl(Action<T> action,
                          Redirect<T> redirect,
                          Validator validator,
                          T pojo,
                          List<FormResponse.Error> errors) {
    this.action = action;
    this.redirect = redirect;
    this.validator = validator;
    this.pojo = pojo;
    this.errors = errors;
  }

  public Reply<?> reply() {
    T updated = pojo;

    Set<ConstraintViolation<T>> violations = validator.validate(pojo);
    addAll(violations);

    if (isValid() && action != null) {
      try {
        updated = action.execute(pojo);
      } catch (Exception e) {
        errors.add(new ErrorImpl(null, e));
      }
    }

    if (isValid() && redirect != null) {
      redirectUrl = redirect.getUrl(updated);
    }

    Context context = null;
    FormJson json = new FormJson(context, errors, redirectUrl);
    return Reply.with(json).as(Json.class);
  }

  private boolean isValid() {
    return errors.isEmpty();
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }

  private void addAll(Set<ConstraintViolation<T>> violations) {
    Iterable<FormResponse.Error> iter = Iterables.transform(violations, new ToError());
    List<FormResponse.Error> list = ImmutableList.copyOf(iter);
    errors.addAll(list);
  }

  private class ToError implements Function<ConstraintViolation<T>, FormResponse.Error> {
    @Override
    public FormResponse.Error apply(ConstraintViolation<T> input) {
      return new ErrorImpl(input);
    }
  }

}