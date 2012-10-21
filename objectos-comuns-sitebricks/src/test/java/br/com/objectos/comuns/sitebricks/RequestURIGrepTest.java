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
package br.com.objectos.comuns.sitebricks;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class RequestURIGrepTest {

  public void equal_to() {
    RequestURIGrep grep = RequestURIGrep.matchUris()
        .equalTo("/login")
        .build();

    assertThat(grep.matches("/login"), is(true));
    assertThat(grep.matches("/login/whatever"), is(false));
    assertThat(grep.matches("/log"), is(false));
  }

  public void ends_with() {
    RequestURIGrep grep = RequestURIGrep.matchUris()
        .endsWith(".css")
        .build();

    assertThat(grep.matches("/style.css"), is(true));
    assertThat(grep.matches("/dir/style.css"), is(true));
    assertThat(grep.matches("/script.js"), is(false));
    assertThat(grep.matches("/dir/script.js"), is(false));
  }

  public void starts_with() {
    RequestURIGrep grep = RequestURIGrep.matchUris()
        .startsWith("/restricted")
        .build();

    assertThat(grep.matches("/restricted"), is(true));
    assertThat(grep.matches("/restricted/secret"), is(true));
    assertThat(grep.matches("/restricted/secret.pdf"), is(true));
    assertThat(grep.matches("/open"), is(false));
    assertThat(grep.matches("/open/restricted"), is(false));
  }

  public void combination() {
    RequestURIGrep grep0 = RequestURIGrep.matchUris().equalTo("/login").build();
    RequestURIGrep grep1 = RequestURIGrep.matchUris().endsWith(".css").build();
    RequestURIGrep grep2 = RequestURIGrep.matchUris().startsWith("/restricted").build();

    RequestURIGrep grep = RequestURIGrep.matchUris()
        .from(grep0)
        .from(grep1)
        .from(grep2)
        .build();

    assertThat(grep.matches("/login"), is(true));
    assertThat(grep.matches("/login/whatever"), is(false));
    assertThat(grep.matches("/log"), is(false));

    assertThat(grep.matches("/style.css"), is(true));
    assertThat(grep.matches("/dir/style.css"), is(true));
    assertThat(grep.matches("/script.js"), is(false));
    assertThat(grep.matches("/dir/script.js"), is(false));

    assertThat(grep.matches("/restricted"), is(true));
    assertThat(grep.matches("/restricted/secret"), is(true));
    assertThat(grep.matches("/restricted/secret.pdf"), is(true));
    assertThat(grep.matches("/open"), is(false));
    assertThat(grep.matches("/open/restricted"), is(false));
  }

}