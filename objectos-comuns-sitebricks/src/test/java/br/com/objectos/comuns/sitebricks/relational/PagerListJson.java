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
package br.com.objectos.comuns.sitebricks.relational;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class PagerListJson {

  private boolean empty;

  private List<Map<?, ?>> rows;

  @JsonIgnore
  private Object pager;

  public boolean isEmpty() {
    return empty;
  }

  public List<Map<?, ?>> getRows() {
    return rows;
  }

  public Object getPager() {
    return pager;
  }

  public void setEmpty(boolean empty) {
    this.empty = empty;
  }

  public void setRows(List<Map<?, ?>> rows) {
    this.rows = rows;
  }

  public void setPager(Object pager) {
    this.pager = pager;
  }

}