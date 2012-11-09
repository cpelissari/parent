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
package br.com.objectos.comuns.sitebricks.page;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.com.objectos.comuns.sitebricks.DefaultRequestWrapper;

import com.google.sitebricks.headless.Request;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Pojo {

  private int id;

  @NotNull
  private String notNull;

  @Min(value = 0)
  private int positive;

  private boolean fail;

  public Pojo() {
  }

  public Pojo(Request request) {
    DefaultRequestWrapper wrapper = new DefaultRequestWrapper(request);
    this.notNull = wrapper.param("notNull");

    Integer positive = wrapper.integerParam("positive");
    this.positive = positive != null ? positive.intValue() : 0;

    this.fail = wrapper.booleanParam("fail");
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return notNull;
  }

  public int getPositive() {
    return positive;
  }

  public boolean isFail() {
    return fail;
  }

}