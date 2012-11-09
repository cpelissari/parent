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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.testng.annotations.Test;

import br.com.objectos.comuns.sitebricks.AbstractJettyTest;
import br.com.objectos.comuns.sitebricks.form.FormResponseJson.ErrorJson;

import com.google.sitebricks.client.WebResponse;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.client.transport.Text;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class FormTest extends AbstractJettyTest {

  public void normal_case() {
    WebResponse response = post("?notNull=abc&positive=5");
    assertThat(response.status(), equalTo(HttpServletResponse.SC_OK));

    FormResponseJson res = response.to(FormResponseJson.class).using(Json.class);
    assertThat(res.isValid(), equalTo(true));

    String url = res.getRedirectUrl();
    assertThat(url, equalTo("http://localhost:4000/pojo/1234"));
  }

  public void jsr303_single_validation_failed() {
    WebResponse response = post("");
    assertThat(response.status(), equalTo(HttpServletResponse.SC_OK));

    FormResponseJson res = response.to(FormResponseJson.class).using(Json.class);
    assertThat(res.isValid(), equalTo(false));

    List<ErrorJson> errors = res.getErrors();
    assertThat(errors.size(), equalTo(1));

    ErrorJson e0 = errors.get(0);
    assertThat(e0.getName(), equalTo("notNull"));
    assertThat(e0.getMessage(), not(equalTo("")));

    String url = res.getRedirectUrl();
    assertThat(url, is(nullValue()));
  }

  public void jsr303_multiple_validation_failed() {
    WebResponse response = post("?positive=-5");
    assertThat(response.status(), equalTo(HttpServletResponse.SC_OK));

    FormResponseJson res = response.to(FormResponseJson.class).using(Json.class);
    assertThat(res.isValid(), equalTo(false));

    List<ErrorJson> errors = res.getErrors();
    Collections.sort(errors);
    assertThat(errors.size(), equalTo(2));

    ErrorJson e0 = errors.get(0);
    assertThat(e0.getName(), equalTo("notNull"));
    assertThat(e0.getMessage(), not(equalTo("")));

    ErrorJson e1 = errors.get(1);
    assertThat(e1.getName(), equalTo("positive"));
    assertThat(e1.getMessage(), not(equalTo("")));

    String url = res.getRedirectUrl();
    assertThat(url, is(nullValue()));
  }

  public void custom_validation_should_fail() {
    WebResponse response = post("?notNull=abc&positive=5&fail=true");
    assertThat(response.status(), equalTo(HttpServletResponse.SC_OK));

    FormResponseJson res = response.to(FormResponseJson.class).using(Json.class);
    assertThat(res.isValid(), equalTo(false));

    List<ErrorJson> errors = res.getErrors();
    assertThat(errors.size(), equalTo(1));

    ErrorJson e0 = errors.get(0);
    assertThat(e0.getName(), equalTo("fail"));
    assertThat(e0.getMessage(), equalTo("fail!!!"));
  }

  private WebResponse post(String query) {
    return clientOf("pojo/form" + query)
        .transports(String.class)
        .over(Text.class)
        .post("");
  }

}