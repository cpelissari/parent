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

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.sitebricks.headless.Reply;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class FormImpl<T> implements Form<T> {

  private final T pojo;

  private final Validator validator;

  private final List<FormResponse.Error> errors = newArrayList();

  private CreateAction<T> createAction;
  private UpdateAction<T> updateAction;
  private Redirect<T> redirectToCallback;

  public FormImpl(T pojo, Validator validator) {
    this.pojo = pojo;
    this.validator = validator;
  }

  @Override
  public WhenConditionBuilder<T> when(boolean condition) {
    return new WhenConditionBuilderImpl(condition);
  }

  @Override
  public Form<T> withCreateAction(CreateAction<T> action) {
    this.createAction = action;
    return this;
  }

  @Override
  public Form<T> withUpdateAction(UpdateAction<T> action) {
    this.updateAction = action;
    return this;
  }

  @Override
  public Form<T> withRedirect(Redirect<T> callback) {
    this.redirectToCallback = callback;
    return this;
  }

  @Override
  public Reply<?> create() {
    FormResponseImpl<T> impl;
    impl = new FormResponseImpl<T>(
        createAction, redirectToCallback, validator, pojo, errors);

    return impl.reply();
  }

  @Override
  public Reply<?> update() {
    FormResponseImpl<T> impl;
    impl = new FormResponseImpl<T>(
        updateAction, redirectToCallback, validator, pojo, errors);

    return impl.reply();
  }

  private class WhenConditionBuilderImpl implements WhenConditionBuilder<T> {
    private final boolean condition;

    public WhenConditionBuilderImpl(boolean condition) {
      this.condition = condition;
    }

    @Override
    public MessageBuilder<T> addMessage(String messageTemplate, Object... args) {
      String msg = String.format(messageTemplate, args);
      return addMessage(msg);
    }

    @Override
    public MessageBuilder<T> addMessage(final String message) {
      return new MessageBuilder<T>() {
        @Override
        public Form<T> toForm() {
          return add(null, message);
        }

        @Override
        public Form<T> toField(String name) {
          return add(name, message);
        }

        private Form<T> add(String name, String message) {
          if (condition) {
            errors.add(new ErrorImpl(name, message));
          }
          return FormImpl.this;
        }
      };
    }
  }

}