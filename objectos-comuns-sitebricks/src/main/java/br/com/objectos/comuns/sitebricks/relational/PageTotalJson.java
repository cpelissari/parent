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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class PageTotalJson implements PageTotal {

  private final int number;

  private final int length;

  private final int total;

  public PageTotalJson(int number, int length, int total) {
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
    return number + 1 < getPageCount();
  }

  @Override
  public int getPrevious() {
    return isHasPrevious() ? number - 1 : 0;
  }

  @Override
  public int getNumber() {
    return number;
  }

  @Override
  public int getNext() {
    return isHasNext() ? number + 1 : number;
  }

  @Override
  public int getPageCount() {
    return total / length + 1;
  }

  @Override
  public int getTotal() {
    return total;
  }

}