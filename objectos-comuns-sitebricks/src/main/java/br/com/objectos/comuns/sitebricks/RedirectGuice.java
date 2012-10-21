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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class RedirectGuice implements Redirect {

  private final Provider<HttpServletRequest> requestProvider;

  private final Provider<HttpSession> sessionProvider;

  @Inject
  public RedirectGuice(Provider<HttpServletRequest> requestProvider,
                       Provider<HttpSession> sessionProvider) {
    this.requestProvider = requestProvider;
    this.sessionProvider = sessionProvider;
  }

  @Override
  public String get() {
    HttpSession session = sessionProvider.get();
    Object o = session.getAttribute(Redirect.class.getName());
    return o != null ? o.toString() : null;
  }

  @Override
  public void set() {
    HttpServletRequest req = requestProvider.get();
    StringBuffer url = req.getRequestURL();

    String queryString = req.getQueryString();
    if (queryString != null) {
      url.append("?");
      url.append(queryString);
    }

    HttpSession session = sessionProvider.get();
    session.setAttribute(Redirect.class.getName(), url.toString());
  }

}