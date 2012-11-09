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

import static com.google.common.collect.Lists.transform;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.net.URISyntaxException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.objectos.comuns.io.Entity;
import br.com.objectos.comuns.io.EntityToString;
import br.com.objectos.comuns.io.FakeEntities;
import br.com.objectos.comuns.io.ParsedFixedLines;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class FixedFileSingleLineTest {

  private List<Entity> entities;

  @BeforeClass
  public void setUp() throws URISyntaxException {
    String line = "true   true    1.23  SP    123    2012-09-09123 abc   ";
    ParsedFixedLines lines = FixedFile.parseString(line)
        .getLines();

    Iterable<Entity> entities = Iterables.transform(lines, new ToEntity());
    this.entities = ImmutableList.copyOf(entities);
  }

  public void test() {
    List<Entity> fakes = ImmutableList.of(FakeEntities.LINE_1);
    List<String> expected = transform(fakes, new EntityToString());

    List<String> res = transform(entities, new EntityToString());
    assertThat(res.size(), equalTo(1));
    assertThat(res, equalTo(expected));
  }

  private class ToEntity implements Function<br.com.objectos.comuns.io.FixedLine, Entity> {
    @Override
    public Entity apply(br.com.objectos.comuns.io.FixedLine input) {
      return new FixedLineEntityBuilder(input).build();
    }
  }

}