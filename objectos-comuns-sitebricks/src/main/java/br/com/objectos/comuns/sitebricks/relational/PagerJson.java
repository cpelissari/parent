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

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@JsonAutoDetect(getterVisibility = Visibility.PUBLIC_ONLY, fieldVisibility = Visibility.NONE)
class PagerJson implements Pager {

  private final int number;

  private final int length;

  private final int total;

  public PagerJson(List<?> rows) {
    this.number = 0;
    this.length = Integer.MAX_VALUE;
    this.total = rows.size();
  }

  public PagerJson(int number, int length, int total) {
    this.number = number;
    this.length = length;
    this.total = total;
  }

  @Override
  public boolean isHasPrevious() {
    return number > 0;
  }

  @Override
  public boolean isHasNext() {
    return number + 1 < getPageTotal();
  }

  @Override
  public boolean isEmpty() {
    return total == 0;
  }

  @Override
  public int getFirstIndex() {
    return number * length + 1;
  }

  @Override
  public int getLastIndex() {
    int last = getFirstIndex() + length - 1;
    return last > total ? total : last;
  }

  @Override
  public int getPreviousPage() {
    return isHasPrevious() ? number - 1 : 0;
  }

  @Override
  public int getPage() {
    return number;
  }

  @Override
  public int getNextPage() {
    return isHasNext() ? number + 1 : number;
  }

  @Override
  public int getPageTotal() {
    return total / length + 1;
  }

  @Override
  public int getTotal() {
    return total;
  }

}