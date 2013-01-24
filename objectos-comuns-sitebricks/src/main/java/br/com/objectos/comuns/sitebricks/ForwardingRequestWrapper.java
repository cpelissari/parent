/*
 * Copyright 2013 Objectos, Fábrica de Software LTDA.
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

import org.joda.time.LocalDate;

import br.com.objectos.comuns.matematica.financeira.Percentual;
import br.com.objectos.comuns.relational.search.Page;
import br.com.objectos.comuns.sitebricks.relational.SearchString;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class ForwardingRequestWrapper implements RequestWrapper {

  protected abstract RequestWrapper delegate();

  @Override
  public boolean booleanParam(String param) {
    return delegate().booleanParam(param);
  }

  @Override
  public <E extends Enum<E>> E enumParam(Class<E> enumClass, String param) {
    return delegate().enumParam(enumClass, param);
  }

  @Override
  public String param(String param) {
    return delegate().param(param);
  }

  @Override
  public LocalDate localDateParam(String param) {
    return delegate().localDateParam(param);
  }

  @Override
  public LocalDate localDateParam(LocalDateFormat format, String param) {
    return delegate().localDateParam(format, param);
  }

  @Override
  public Double doubleParam(String param) {
    return delegate().doubleParam(param);
  }

  @Override
  public Integer integerParam(String param) {
    return delegate().integerParam(param);
  }

  @Override
  public Long longParam(String param) {
    return delegate().longParam(param);
  }

  @Override
  public Percentual percentualParam(String param) {
    return delegate().percentualParam(param);
  }

  @Override
  public Page getPage() {
    return delegate().getPage();
  }

  @Override
  public SearchString getSearchString(String param) {
    return delegate().getSearchString(param);
  }

}