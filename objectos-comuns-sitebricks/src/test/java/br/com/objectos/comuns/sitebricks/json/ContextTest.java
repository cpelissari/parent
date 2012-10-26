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
package br.com.objectos.comuns.sitebricks.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.sitebricks.ObjectosComunsSitebricksTestModule;

import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { ObjectosComunsSitebricksTestModule.class })
public class ContextTest {

  @Inject
  private ObjectMapper mapper;

  public void map_test() throws JsonGenerationException, JsonMappingException, IOException {
    Context context = Context.of();
    context.put("name", "abc");
    context.put("number", 123);

    String res = mapper.writeValueAsString(context);

    assertThat(res, equalTo("{\"name\":\"abc\",\"number\":123}"));
  }

  public void root_test() throws JsonGenerationException, JsonMappingException, IOException {
    Context context = Context.of(new Pojo("abc", 123));

    String res = mapper.writeValueAsString(context);

    assertThat(res, equalTo("{\"name\":\"abc\",\"number\":123}"));
  }

  public void merge_test() throws JsonGenerationException, JsonMappingException, IOException {
    Context context = Context.of(new Pojo("abc", 123));
    context.put("m", new Pojo("n", 9));

    String res = mapper.writeValueAsString(context);

    assertThat(res,
        equalTo("{\"name\":\"abc\",\"number\":123,\"m\":{\"name\":\"n\",\"number\":9}}"));
  }

  public static class Pojo {
    String name;
    int number;

    public Pojo(String name, int number) {
      this.name = name;
      this.number = number;
    }
  }

}