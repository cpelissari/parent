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
package br.com.objectos.comuns.sitebricks.page;

import br.com.objectos.comuns.sitebricks.BaseUrl;
import br.com.objectos.comuns.sitebricks.form.Form;
import br.com.objectos.comuns.sitebricks.form.Forms;

import com.google.inject.Inject;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.http.Post;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class PojoFormService {

  private final BaseUrl baseUrl;

  private final Forms forms;

  @Inject
  public PojoFormService(BaseUrl baseUrl, Forms forms) {
    this.baseUrl = baseUrl;
    this.forms = forms;
  }

  @Post
  public Reply<?> post(Request request) {
    Pojo pojo = new Pojo(request);

    return forms.newFormFor(pojo)

        .when(pojo.isFail()).addMessage("fail!!!").toField("fail")

        .withCreateAction(new PojoCreate())
        .withRedirect(new PojoRedirect())

        .create();
  }

  private class PojoCreate implements Form.CreateAction<Pojo> {
    @Override
    public Pojo execute(Pojo pojo) {
      pojo.setId(1234);
      return pojo;
    }
  }

  private class PojoRedirect implements Form.Redirect<Pojo> {
    @Override
    public String getUrl(Pojo pojo) {
      return baseUrl.get() + "/pojo/" + pojo.getId();
    }
  }

}