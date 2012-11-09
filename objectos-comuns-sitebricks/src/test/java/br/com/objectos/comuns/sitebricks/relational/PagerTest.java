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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class PagerTest {

  public void at_the_first_page_without_next() {
    Pager res = new PagerJson(0, 10, 9);

    assertThat(res.isHasPrevious(), is(false));
    assertThat(res.isHasNext(), is(false));
    assertThat(res.getFirstIndex(), equalTo(1));
    assertThat(res.getLastIndex(), equalTo(9));
    assertThat(res.getPreviousPage(), equalTo(0));
    assertThat(res.getPage(), equalTo(0));
    assertThat(res.getNextPage(), equalTo(0));
    assertThat(res.getPageTotal(), equalTo(1));
  }

  public void at_the_first_page_with_next() {
    Pager res = new PagerJson(0, 10, 15);

    assertThat(res.isHasPrevious(), is(false));
    assertThat(res.isHasNext(), is(true));
    assertThat(res.getFirstIndex(), equalTo(1));
    assertThat(res.getLastIndex(), equalTo(10));
    assertThat(res.getPreviousPage(), equalTo(0));
    assertThat(res.getPage(), equalTo(0));
    assertThat(res.getNextPage(), equalTo(1));
    assertThat(res.getPageTotal(), equalTo(2));
  }

  public void at_middle() {
    Pager res = new PagerJson(2, 10, 78);

    assertThat(res.isHasPrevious(), is(true));
    assertThat(res.isHasNext(), is(true));
    assertThat(res.getFirstIndex(), equalTo(21));
    assertThat(res.getLastIndex(), equalTo(30));
    assertThat(res.getPreviousPage(), equalTo(1));
    assertThat(res.getPage(), equalTo(2));
    assertThat(res.getNextPage(), equalTo(3));
    assertThat(res.getPageTotal(), equalTo(8));
  }

  public void at_the_end() {
    Pager res = new PagerJson(7, 10, 78);

    assertThat(res.isHasPrevious(), is(true));
    assertThat(res.isHasNext(), is(false));
    assertThat(res.getFirstIndex(), equalTo(71));
    assertThat(res.getLastIndex(), equalTo(78));
    assertThat(res.getPreviousPage(), equalTo(6));
    assertThat(res.getPage(), equalTo(7));
    assertThat(res.getNextPage(), equalTo(7));
    assertThat(res.getPageTotal(), equalTo(8));
  }

}