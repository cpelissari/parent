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

import java.util.List;

import br.com.objectos.way.view.Context;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class FormJson implements FormResponse {

  private final Context context;

  private final List<Error> errors;

  private final String redirectUrl;

  public FormJson(Context context, List<Error> errors, String redirectUrl) {
    this.context = context;
    this.errors = errors;
    this.redirectUrl = redirectUrl;
  }

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