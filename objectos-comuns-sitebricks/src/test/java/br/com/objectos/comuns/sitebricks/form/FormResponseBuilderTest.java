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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;

import org.testng.annotations.Test;

import br.com.objectos.comuns.sitebricks.form.FormResponse.Error;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class FormResponseBuilderTest {

  public void default_response() {
    FormResponse res = new FormResponseBuilder()
        .get();

    assertThat(res.isValid(), equalTo(true));

    List<Error> errors = res.getErrors();
    assertThat(errors.size(), equalTo(0));

    assertThat(res.getRedirectUrl(), nullValue());
  }

  public void invalid_response() {
    FormResponse res = new FormResponseBuilder()

        .addMessage("a").toField("f")

        .get();

    assertThat(res.isValid(), equalTo(false));

    List<Error> errors = res.getErrors();
    assertThat(errors.size(), equalTo(1));

    Error e0 = errors.get(0);
    assertThat(e0.getName(), equalTo("f"));
    assertThat(e0.getMessage(), equalTo("a"));

    assertThat(res.getRedirectUrl(), nullValue());
  }

  public void redirect_url() {
    FormResponse res = new FormResponseBuilder()

        .redirectTo("http://localhost/whatever")

        .get();

    assertThat(res.getRedirectUrl(), equalTo("http://localhost/whatever"));
  }

  public void form_message() {
    FormResponse res = new FormResponseBuilder()

        .addMessage("a").toForm()

        .get();

    assertThat(res.isValid(), equalTo(false));

    List<Error> errors = res.getErrors();
    assertThat(errors.size(), equalTo(1));

    Error e0 = errors.get(0);
    assertThat(e0.getName(), nullValue());
    assertThat(e0.getMessage(), equalTo("a"));
  }

  public void form_message_template() {
    FormResponse res = new FormResponseBuilder()

        .addMessage("%s:%d", "a", Integer.valueOf(1)).toField("f")

        .get();

    assertThat(res.isValid(), equalTo(false));

    List<Error> errors = res.getErrors();
    assertThat(errors.size(), equalTo(1));

    Error e0 = errors.get(0);
    assertThat(e0.getName(), equalTo("f"));
    assertThat(e0.getMessage(), equalTo("a:1"));
  }

}