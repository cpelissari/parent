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

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Injector;
import com.google.sitebricks.client.Web;
import com.google.sitebricks.client.Web.FormatBuilder;
import com.google.sitebricks.client.WebClient;
import com.google.sitebricks.client.WebResponse;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.client.transport.Text;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractWebIntegrationTest {

  private static final String STD_RESOURCE_DIR = "src/test/resources";

  private static final Jetty server;

  static {
    server = new Jetty(STD_RESOURCE_DIR);
  }

  private List<WebClient<Object>> jsonClientCache;
  private List<WebClient<String>> textClientCache;

  @BeforeSuite
  public void start() throws Exception {
    server.start();
  }

  @BeforeClass
  public void injectMembers() {
    jsonClientCache = newArrayList();
    textClientCache = newArrayList();
    server.getInjector().injectMembers(this);
  }

  @AfterSuite
  public void stop() throws Exception {
    server.stop();
  }

  @AfterClass(alwaysRun = true)
  public void closeWebClients() {
    for (WebClient<?> client : jsonClientCache) {
      client.close();
    }
    for (WebClient<?> client : textClientCache) {
      client.close();
    }
  }

  protected String getBaseUrl() {
    return server.getBaseUrl();
  }

  protected Injector getInjector() {
    return server.getInjector();
  }

  protected Map<String, String> login(String login) {
    String senha = login;
    return login(login, senha);
  }

  protected Map<String, String> login(String login, String senha) {
    String url = String.format("api/login?login=%s&senha=%s", login, senha);

    WebResponse response = webClientOf(url).post("");
    Map<String, String> headers = response.getHeaders();
    String cookie = headers.get("Set-Cookie");
    Map<String, String> cookies = ImmutableMap.of("Cookie", cookie);

    return cookies;
  }

  protected WebClient<Object> jsonClientOf(String url, Map<String, String> headers) {
    WebClient<Object> client = clientOf(url, headers).transports(Object.class).over(Json.class);
    jsonClientCache.add(client);
    return client;
  }

  protected WebClient<String> webClientOf(String url) {
    Map<String, String> headers = ImmutableMap.of();
    return webClientOf(url, headers);
  }

  protected WebClient<String> webClientOf(String url, Map<String, String> headers) {
    WebClient<String> client = clientOf(url, headers).transports(String.class).over(Text.class);
    textClientCache.add(client);
    return client;
  }

  private FormatBuilder clientOf(String url, Map<String, String> headers) {
    String baseUrl = server.getBaseUrl();
    String _url = Joiner.on("/").skipNulls().join(baseUrl, url);
    return server.getInjector().getInstance(Web.class).clientOf(_url, headers);
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