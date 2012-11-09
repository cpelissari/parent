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
public class FakePageTotalJson implements Pager {

  private boolean hasPrevious;
  private boolean hasNext;

  private int firstIndex;
  private int lastIndex;

  private int previous;
  private int number;
  private int next;

  private int pageCount;
  private int total;

  @Override
  public boolean isHasPrevious() {
    return hasPrevious;
  }
  @Override
  public boolean isHasNext() {
    return hasNext;
  }
  @Override
  public boolean isEmpty() {
    return total == 0;
  }
  @Override
  public int getFirstIndex() {
    return firstIndex;
  }
  @Override
  public int getLastIndex() {
    return lastIndex;
  }
  @Override
  public int getPreviousPage() {
    return previous;
  }
  @Override
  public int getPage() {
    return number;
  }
  @Override
  public int getNextPage() {
    return next;
  }
  @Override
  public int getPageTotal() {
    return pageCount;
  }
  @Override
  public int getTotal() {
    return total;
  }
  public void setHasPrevious(boolean hasPrevious) {
    this.hasPrevious = hasPrevious;
  }
  public void setHasNext(boolean hasNext) {
    this.hasNext = hasNext;
  }
  public void setPrevious(int previous) {
    this.previous = previous;
  }
  public void setFirstIndex(int firstIndex) {
    this.firstIndex = firstIndex;
  }
  public void setLastIndex(int lastIndex) {
    this.lastIndex = lastIndex;
  }
  public void setNumber(int number) {
    this.number = number;
  }
  public void setNext(int next) {
    this.next = next;
  }
  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }
  public void setTotal(int total) {
    this.total = total;
  }

}