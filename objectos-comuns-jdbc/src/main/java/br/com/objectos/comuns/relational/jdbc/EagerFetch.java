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
package br.com.objectos.comuns.relational.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import br.com.objectos.comuns.relational.search.ResultSetLoader;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class EagerFetch<T> {

  public static interface Condition {
    boolean isSatisfied();
  }

  private final ResultSet resultSet;

  private ResultSetLoader<T> loader;
  private Condition onlyIf;
  private Condition whileTrue;

  public EagerFetch(ResultSet resultSet) {
    this.resultSet = resultSet;
  }

  public EagerFetch<T> andLoadWith(ResultSetLoader<T> loader) {
    this.loader = loader;
    return this;
  }

  public EagerFetch<T> onlyIf(Condition condition) {
    this.onlyIf = condition;
    return this;
  }

  public List<T> whileTrue(Condition condition) {
    this.whileTrue = condition;

    Iterator<ResultSet> iter = new ResultSetIterator();
    Iterator<T> res = Iterators.transform(iter, new ResultSetFunc());

    List<T> list = ImmutableList.copyOf(res);
    return list;
  }

  private boolean rsNext() {
    try {
      return resultSet.next();
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  private void rsPrevious() {
    try {
      resultSet.previous();
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  private class ResultSetIterator implements Iterator<ResultSet> {

    private boolean cached = false;
    private boolean hasNext = false;
    private boolean first = true;

    @Override
    public boolean hasNext() {
      if (!cached) {
        hasNext = computeHasNext();
        cached = true;
      }

      return hasNext;
    }

    private boolean computeHasNext() {
      if (first) {
        boolean satisfied = onlyIf.isSatisfied();
        return satisfied;
      } else {
        boolean next = rsNext();
        if (next && onlyIf.isSatisfied() && whileTrue.isSatisfied()) {
          return true;
        } else {
          if (next) {
            rsPrevious();
          }
          return false;
        }
      }
    }

    @Override
    public ResultSet next() {
      cached = false;

      if (first) {
        first = false;
      }

      return resultSet;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private class ResultSetFunc implements Function<ResultSet, T> {

    @Override
    public T apply(ResultSet input) {
      try {
        return loader.load(input);
      } catch (SQLException e) {
        throw new SQLRuntimeException(e);
      }
    }

  }

}