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

import java.util.List;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.sitebricks.ObjectosComunsSitebricksTestModule;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.sitebricks.http.Put;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { ObjectosComunsSitebricksTestModule.class })
public class JsonsTest {

  @Inject
  private Jsons jsons;

  public void add_value() {
    PojoJson pojo = new PojoJson(1, "abc");

    String res = jsons.builder()
        .addValue(pojo)
        .toString();

    assertThat(res, equalTo("{\"id\":1,\"name\":\"abc\"}"));
  }

  public void add() {
    PojoJson pojo = new PojoJson(1, "abc");

    String res = jsons.builder()
        .add("pojo", pojo)
        .toString();

    assertThat(res, equalTo("{\"pojo\":{\"id\":1,\"name\":\"abc\"}}"));
  }

  public void both() {
    PojoJson pojo = new PojoJson(1, "abc");
    PojoJson child = new PojoJson(2, "xyz");

    String res = jsons.builder()
        .addValue(pojo)
        .add("c", child)
        .toString();

    assertThat(res, equalTo("{\"id\":1,\"name\":\"abc\",\"c\":{\"id\":2,\"name\":\"xyz\"}}"));
  }

  public void append() {
    PojoJson pojo = new PojoJson(1, "abc");

    String res = jsons.builder()
        .add("pojo", pojo)
        .to("pojo").append("k1", "val1")
        .to("pojo").append("k2", "val2")

        .toString();

    assertThat(res,
        equalTo("{\"pojo\":{\"id\":1,\"name\":\"abc\",\"k1\":\"val1\",\"k2\":\"val2\"}}"));
  }

  public void append_form() {
    PojoJson pojo = new PojoJson(1, "1");

    String res = jsons.builder()
        .add("pojo", pojo)

        .to("pojo").appendForm(Put.class, "api/crud")

        .toString();

    assertThat(res, equalTo("{\"pojo\":{\"id\":1,\"name\":\"1\"," +
        "\"form\":{\"action\":\"http://fake/api/crud\",\"method\":\"put\"}}}"));
  }

  public void append_to_list() {
    PojoJson p1 = new PojoJson(1, "1");
    PojoJson p2 = new PojoJson(2, "2");
    List<PojoJson> pojos = ImmutableList.of(p1, p2);

    String res = jsons.builder()
        .add("pojos", pojos)

        .to("pojos").append("k", "v")

        .toString();

    assertThat(
        res,
        equalTo("{\"pojos\":[{\"id\":1,\"name\":\"1\",\"k\":\"v\"},{\"id\":2,\"name\":\"2\",\"k\":\"v\"}]}"));

  }

}