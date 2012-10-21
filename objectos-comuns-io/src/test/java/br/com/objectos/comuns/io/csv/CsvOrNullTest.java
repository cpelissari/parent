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
package br.com.objectos.comuns.io.csv;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.net.URISyntaxException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.objectos.comuns.io.Line;
import br.com.objectos.comuns.io.ParsedLines;

import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class CsvOrNullTest {

  private List<Line> lines;

  @BeforeClass
  public void setUp() throws URISyntaxException {
    String line = ",";
    ParsedLines lines = CsvFile.parseString(line)
        .onCommas()
        .getLines();

    this.lines = ImmutableList.copyOf(lines);
  }

  public void or_null() {
    Line l0 = lines.get(0);

    assertThat(l0.column(0).orNull(String.class), is(nullValue()));
  }

  public void or_instance() {
    Line l0 = lines.get(0);

    assertThat(l0.column(1).or(String.class, "default"), equalTo("default"));
  }

}