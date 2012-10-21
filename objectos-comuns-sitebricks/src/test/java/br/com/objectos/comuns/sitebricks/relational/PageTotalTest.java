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
public class PageTotalTest {

  public void at_the_first_page_without_next() {
    PageTotalJson res = new PageTotalJson(0, 10, 9);

    assertThat(res.isHasPrevious(), is(false));
    assertThat(res.isHasNext(), is(false));
    assertThat(res.getPrevious(), equalTo(0));
    assertThat(res.getNumber(), equalTo(0));
    assertThat(res.getNext(), equalTo(0));
    assertThat(res.getPageCount(), equalTo(1));
  }

  public void at_the_first_page_with_next() {
    PageTotalJson res = new PageTotalJson(0, 10, 15);

    assertThat(res.isHasPrevious(), is(false));
    assertThat(res.isHasNext(), is(true));
    assertThat(res.getPrevious(), equalTo(0));
    assertThat(res.getNumber(), equalTo(0));
    assertThat(res.getNext(), equalTo(1));
    assertThat(res.getPageCount(), equalTo(2));
  }

  public void at_middle() {
    PageTotalJson res = new PageTotalJson(2, 10, 78);

    assertThat(res.isHasPrevious(), is(true));
    assertThat(res.isHasNext(), is(true));
    assertThat(res.getPrevious(), equalTo(1));
    assertThat(res.getNumber(), equalTo(2));
    assertThat(res.getNext(), equalTo(3));
    assertThat(res.getPageCount(), equalTo(8));
  }

  public void at_the_end() {
    PageTotalJson res = new PageTotalJson(7, 10, 78);

    assertThat(res.isHasPrevious(), is(true));
    assertThat(res.isHasNext(), is(false));
    assertThat(res.getPrevious(), equalTo(6));
    assertThat(res.getNumber(), equalTo(7));
    assertThat(res.getNext(), equalTo(7));
    assertThat(res.getPageCount(), equalTo(8));
  }

}