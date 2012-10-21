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
package br.com.objectos.comuns.relational.jdbc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class InsertsTest {

  public void fluent_builder_test() {
    List<Insert> inserts = Inserts

        .firstInto("A")
        .value("A0", 0)
        .value("A1", 1)
        .onGeneratedKey(new GeneratedKeyCallback() {
          @Override
          public void set(ResultSet rs) throws SQLException {
          }
        })

        .andThenInto("B")
        .value("B0", 0)
        .value("B1", 0)

        .list();

    assertThat(inserts.size(), equalTo(2));

    Insert i0 = inserts.get(0);
    assertThat(i0.toString(), equalTo("INSERT INTO A (`A0`, `A1`) VALUES (?, ?)"));

    Insert i1 = inserts.get(1);
    assertThat(i1.toString(), equalTo("INSERT INTO B (`B0`, `B1`) VALUES (?, ?)"));
  }

}