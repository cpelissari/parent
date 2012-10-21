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

import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
class WebSessionGuice implements WebSession {

  private final Provider<HttpSession> sessions;

  @Inject
  public WebSessionGuice(Provider<HttpSession> sessions) {
    this.sessions = sessions;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T get(Class<T> objectClass) {
    HttpSession session = sessions.get();
    return (T) session.getAttribute(objectClass.getName());
  }

  @Override
  public <T> void set(Class<T> objectClass, T value) {
    HttpSession session = sessions.get();
    session.setAttribute(objectClass.getName(), value);
  }

  @Override
  public void invalidate() {
    HttpSession session = sessions.get();
    session.invalidate();
  }

}