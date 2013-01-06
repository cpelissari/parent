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
package br.com.objectos.comuns.etc.model;

import static br.com.objectos.comuns.etc.model.FakeUsers.USER_A;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.etc.Config;
import br.com.objectos.comuns.etc.EtcFiles;
import br.com.objectos.comuns.etc.EtcTestModule;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { EtcTestModule.class })
public class GlobalTest {

  @Inject
  private Provider<Config> configProvider;

  public void global_dump() throws IOException {
    Global global = FakeGlobals.GLOBAL_USER_A;

    String expect = EtcFiles.readLines("/model/global-usera.yaml");
    System.out.println(expect);

    Config config = configProvider.get();
    String res = config.toString(global);
    System.out.println(res);

    assertThat(res, equalTo(expect));
  }

  public void global_read() throws IOException {
    String text = EtcFiles.readLines("/model/global-usera.yaml");

    Config config = configProvider.get();
    Global res = config.load(text, Global.class);

    User user = res.getUser();
    assertThat(user.getName(), equalTo(USER_A.getName()));
    assertThat(user.getEmail(), equalTo(USER_A.getEmail()));
  }

}