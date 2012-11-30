/*
 * Copyright 2011 Objectos, Fábrica de Software LTDA.
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
package br.com.objectos.comuns.testing.dbunit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import com.google.common.base.Throwables;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ResultSetIterator implements Iterator<ResultSet> {

  private final ResultSet rs;

  private boolean cached = false;
  private boolean hasNext = false;

  public ResultSetIterator(ResultSet rs) {
    this.rs = rs;
  }

  @Override
  public boolean hasNext() {
    if (!cached) {
      try {
        hasNext = rs.next();
        cached = true;
      } catch (SQLException e) {
        throw Throwables.propagate(e);
      }
    }

    return hasNext;
  }

  @Override
  public ResultSet next() {
    cached = false;
    return rs;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}