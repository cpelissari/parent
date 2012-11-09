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
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.com.objectos.comuns.sitebricks.relational.SearchString.Param;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class SearchStringTest {

  public void main_param() {
    SearchString s = new SearchString("abc");

    Param p = s.param();

    assertThat(p.toString(), equalTo("abc"));
  }

  public void main_param_trim() {
    SearchString s = new SearchString(" abc   ");

    Param p = s.param();

    assertThat(p.toString(), equalTo("abc"));
  }

  public void var_param() {
    SearchString s = new SearchString("abc var:xyz");

    Param p = s.param();
    Param var = s.param("var");

    assertThat(p.toString(), equalTo("abc"));
    assertThat(var.toString(), equalTo("xyz"));
  }

  public void var_param_empty() {
    SearchString s = new SearchString("abc var:");

    Param p = s.param();
    Param var = s.param("var");

    assertThat(p.toString(), equalTo("abc"));
    assertTrue(var.isEmpty());
    assertThat(var.toString(), equalTo(""));
  }

  public void is_empty() {
    SearchString s = new SearchString(" ");

    assertTrue(s.isEmpty());
  }

  public void is_empty_var() {
    SearchString s = new SearchString(" var:");

    assertTrue(s.isEmpty());
  }

  public void is_empty_not() {
    SearchString s = new SearchString(" abc");

    assertFalse(s.isEmpty());
  }

  public void is_empty_not_var() {
    SearchString s = new SearchString(" var:xyz");

    assertFalse(s.isEmpty());
  }

}