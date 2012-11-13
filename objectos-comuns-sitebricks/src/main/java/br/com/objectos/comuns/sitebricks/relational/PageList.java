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

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class PageList<T> {

  private final boolean empty;

  private final List<T> rows;

  private final Pager pager;

  public PageList(List<T> rows) {
    this.empty = rows.isEmpty();
    this.rows = rows;
    this.pager = new PagerJson(rows);
  }

  public PageList(List<T> rows, Pager pager) {
    this.empty = rows.isEmpty();
    this.rows = rows;
    this.pager = pager;
  }

  public <E> PageList<E> transform(Function<T, E> function) {
    List<E> lazy = Lists.transform(rows, function);
    List<E> rows = ImmutableList.copyOf(lazy);
    return new PageList<E>(rows, pager);
  }

  public static <T> PageList<T> of(Iterable<T> rows) {
    List<T> list = ImmutableList.copyOf(rows);
    return new PageList<T>(list);
  }

  public boolean isEmpty() {
    return empty;
  }

  public List<T> getRows() {
    return rows;
  }

  public Pager getPager() {
    return pager;
  }

}