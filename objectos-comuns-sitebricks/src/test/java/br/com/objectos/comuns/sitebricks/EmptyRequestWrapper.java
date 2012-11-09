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

import org.joda.time.LocalDate;

import br.com.objectos.comuns.matematica.financeira.Percentual;
import br.com.objectos.comuns.relational.search.Page;
import br.com.objectos.comuns.relational.search.SimplePage;
import br.com.objectos.comuns.sitebricks.relational.SearchString;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class EmptyRequestWrapper implements RequestWrapper {

  @Override
  public boolean booleanParam(String param) {
    return false;
  }

  @Override
  public <E extends Enum<E>> E enumParam(Class<E> enumClass, String param) {
    return null;
  }

  @Override
  public String param(String param) {
    return null;
  }

  @Override
  public LocalDate localDateParam(String param) {
    return null;
  }

  @Override
  public LocalDate localDateParam(LocalDateFormat format, String param) {
    return null;
  }

  @Override
  public Double doubleParam(String param) {
    return null;
  }

  @Override
  public Integer integerParam(String param) {
    return null;
  }

  @Override
  public Long longParam(String param) {
    return null;
  }

  @Override
  public Percentual percentualParam(String param) {
    return null;
  }

  @Override
  public Page getPage() {
    return SimplePage.build()
        .startAt(0)
        .withLengthOf(Integer.MAX_VALUE)
        .get();
  }

  @Override
  public SearchString getSearchString(String param) {
    String string = param(param);
    return new SearchString(string);
  }

}