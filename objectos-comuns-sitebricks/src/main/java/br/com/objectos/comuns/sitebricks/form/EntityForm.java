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

import br.com.objectos.comuns.sitebricks.BaseUrl;
import br.com.objectos.comuns.sitebricks.json.EntityJson;
import br.com.objectos.way.view.Context;

import com.google.sitebricks.headless.Reply;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public interface EntityForm<T extends EntityJson> {

  interface ActionListener<T extends EntityJson> {
    void onCreate(T pojo);
    void onUpdate(T pojo);
    void onDelete(T pojo);
  }

  interface ContextDecorator<T extends EntityJson> {
    void decorate(Context ctx);
  }

  interface Redirect<T extends EntityJson> {
    String getUrl(BaseUrl baseUrl, T pojo);
  }

  EntityForm<T> withActionListener(ActionListener<T> listener);

  EntityForm<T> withContextDecorator(ContextDecorator<T> decorator);

  EntityForm<T> withRedirect(Redirect<T> redirect);

  Reply<?> reply();

}