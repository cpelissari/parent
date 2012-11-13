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

import br.com.objectos.comuns.sitebricks.form.FormResponse.Error;
import br.com.objectos.way.view.Context;

import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class FormResponseBuilder {

  public static interface MessageBuilder {
    FormResponseBuilder toField(String string);
    FormResponseBuilder toForm();
  }

  private Context context;

  private final List<Error> errors = newArrayList();

  private String redirectUrl;

  public FormResponse build() {
    return new Impl();
  }

  public FormResponse get() {
    return build();
  }

  public Reply<?> reply() {
    FormResponse response = get();
    return Reply.with(response).as(Json.class);
  }

  public FormResponseBuilder withContext(Context context) {
    this.context = context;
    return this;
  }

  public WhenBuilder when(boolean condition) {
    return new WhenBuilder(condition);
  }

  public MessageBuilder addMessage(String template, Object... args) {
    String message = String.format(template, args);
    return addMessage(message);
  }

  public MessageBuilder addMessage(final String message) {
    return new MessageBuilder() {
      @Override
      public FormResponseBuilder toField(String field) {
        errors.add(new ErrorImpl(field, message));

        return FormResponseBuilder.this;
      }

      @Override
      public FormResponseBuilder toForm() {
        errors.add(new ErrorImpl(null, message));

        return FormResponseBuilder.this;
      }
    };
  }

  public FormResponseBuilder redirectTo(String url) {
    this.redirectUrl = url;

    return this;
  }

  public class WhenBuilder {

    private final boolean condition;

    public WhenBuilder(boolean condition) {
      this.condition = condition;
    }

    public MessageBuilder addMessage(String messageTemplate, Object... args) {
      return condition ?
          FormResponseBuilder.this.addMessage(messageTemplate, args) : new NoopMessageBuilder();
    }

    public MessageBuilder addMessage(final String message) {
      return condition ?
          FormResponseBuilder.this.addMessage(message) : new NoopMessageBuilder();
    }

  }

  private class NoopMessageBuilder implements MessageBuilder {

    @Override
    public FormResponseBuilder toField(String string) {
      return FormResponseBuilder.this;
    }

    @Override
    public FormResponseBuilder toForm() {
      return FormResponseBuilder.this;
    }

  }

  private class Impl implements FormResponse {

    @Override
    public Context getContext() {
      return context;
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

  }

}