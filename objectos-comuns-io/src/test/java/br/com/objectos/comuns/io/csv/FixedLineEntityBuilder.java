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

import org.joda.time.LocalDate;

import br.com.objectos.comuns.base.br.Estado;
import br.com.objectos.comuns.io.Entity;
import br.com.objectos.comuns.io.EntityPojo;
import br.com.objectos.comuns.util.Booleano;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class FixedLineEntityBuilder implements Entity.Builder {

  private final br.com.objectos.comuns.io.FixedLine line;

  public FixedLineEntityBuilder(br.com.objectos.comuns.io.FixedLine line) {
    this.line = line;
  }

  @Override
  public Entity build() {
    return new EntityPojo(this);
  }

  @Override
  public Boolean booleanValue() {
    return line.column(0, 7).get(Boolean.class);
  }

  @Override
  public Booleano booleanoValue() {
    return line.column(7, 15).get(Booleano.class);
  }

  @Override
  public Double doubleValue() {
    return line.column(15, 21).get(Double.class);
  }

  @Override
  public Estado estadoValue() {
    return line.column(21, 27).get(Estado.class);
  }

  @Override
  public Integer integerValue() {
    return line.column(27, 34).get(Integer.class);
  }

  @Override
  public LocalDate localDate() {
    return line.column(34, 44).get(LocalDate.class);
  }

  @Override
  public Long longValue() {
    return line.column(44, 48).get(Long.class);
  }

  @Override
  public String stringValue() {
    return line.column(48, 54).get(String.class);
  }

}