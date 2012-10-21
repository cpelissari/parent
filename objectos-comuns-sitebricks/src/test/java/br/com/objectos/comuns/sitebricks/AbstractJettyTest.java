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
package br.com.objectos.comuns.sitebricks;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Injector;
import com.google.sitebricks.client.Web;
import com.google.sitebricks.client.Web.FormatBuilder;
import com.google.sitebricks.client.WebResponse;
import com.google.sitebricks.client.transport.Text;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { FakeBootstrapModule.class })
public abstract class AbstractJettyTest {

  public static final String BASE_URL = "http://localhost:4000";
  private static final String STD_RESOURCE_DIR = "src/test/resources";

  private final Jetty server;

  private Injector injector;

  public AbstractJettyTest() {
    server = new Jetty(STD_RESOURCE_DIR);
  }

  @BeforeSuite
  public void start() throws Exception {
    server.start();
    injector = server.getInjector();
  }

  @AfterSuite
  public void stop() throws Exception {
    server.stop();
  }

  protected FormatBuilder clientOf(String... parts) {
    LinkedList<String> list = newLinkedList(Arrays.asList(parts));
    list.addFirst(BASE_URL);

    String url = Joiner.on("/").skipNulls().join(list);
    return injector.getInstance(Web.class).clientOf(url);
  }

  protected FormatBuilder clientOf(String url) {
    String _url = Joiner.on("/").skipNulls().join(BASE_URL, url);
    return injector.getInstance(Web.class).clientOf(_url);
  }

  protected FormatBuilder clientOf(String url, Map<String, String> headers) {
    String _url = Joiner.on("/").skipNulls().join(BASE_URL, url);
    return injector.getInstance(Web.class).clientOf(_url, headers);
  }

  protected WebResponse getTextResponseOf(String url) {
    Map<String, String> headers = ImmutableMap.of();
    return clientOf(url, headers)
        .transports(String.class)
        .over(Text.class)
        .get();
  }

  protected WebResponse getTextResponseOf(String url, Map<String, String> headers) {
    return clientOf(url, headers)
        .transports(String.class)
        .over(Text.class)
        .get();
  }

  protected class ToText implements Function<Element, String> {
    public ToText() {
    }

    @Override
    public String apply(Element input) {
      return input.text();
    }
  }

}