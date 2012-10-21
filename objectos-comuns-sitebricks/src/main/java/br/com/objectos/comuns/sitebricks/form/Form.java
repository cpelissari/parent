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

import com.google.sitebricks.headless.Reply;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public interface Form<T> {

  interface WhenConditionBuilder<T> {
    MessageBuilder<T> addMessage(String message);
    MessageBuilder<T> addMessage(String messageTemplate, Object... args);
  }

  interface MessageBuilder<T> {
    Form<T> toField(String name);
    Form<T> toForm();
  }

  interface Action<T> {
    T execute(T pojo);
  }

  interface CreateAction<T> extends Action<T> {}

  interface UpdateAction<T> extends Action<T> {}

  interface Redirect<T> {
    String getUrl(T pojo);
  }

  WhenConditionBuilder<T> when(boolean condition);

  Form<T> withCreateAction(CreateAction<T> action);

  Form<T> withUpdateAction(UpdateAction<T> action);

  Form<T> withRedirect(Redirect<T> redirect);

  Reply<?> create();
  Reply<?> update();

}