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

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Supplier;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractRequestGetter<T> implements Supplier<T> {

  private final Provider<HttpServletRequest> requestProvider;

  @Inject
  public AbstractRequestGetter(Provider<HttpServletRequest> requestProvider) {
    this.requestProvider = requestProvider;
  }

  @SuppressWarnings("unchecked")
  @Override
  public T get() {
    String key = getKey();
    HttpServletRequest request = requestProvider.get();

    return (T) request.getAttribute(key);
  }

  protected abstract String getKey();

}