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

import static com.google.common.collect.Lists.newArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;

import br.com.objectos.comuns.relational.search.Element;
import br.com.objectos.comuns.relational.search.HasElements;
import br.com.objectos.comuns.relational.search.Join;
import br.com.objectos.comuns.relational.search.Limit;
import br.com.objectos.comuns.relational.search.OrderProperty;
import br.com.objectos.comuns.relational.search.Where;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
abstract class JdbcConfigure {

  private final HasElements sql;

  private String toString;

  public JdbcConfigure(HasElements sql) {
    this.sql = sql;
  }

  public PreparedStatement prepare(Connection connection) {
    try {

      return tryToMake(connection);

    } catch (SQLException e) {

      throw new SQLRuntimeException(e);

    }
  }

  @Override
  public String toString() {
    return toString;
  }

  PreparedStatement tryToMake(Connection connection) throws SQLException {
    List<Join> joins = getJoins();
    List<Element> whereEls = getWhereEls(joins);

    StringBuilder sql = new StringBuilder();

    sql.append(New.Line);
    makeOperation(sql, joins);

    makeWhere(sql, whereEls);

    makeOrderBy(sql);

    limitIfPossible(sql);

    toString = sql.toString();
    getLogger().debug(toString);
    PreparedStatement stmt = connection.prepareStatement(toString);

    CountingStatement countingStatement = new CountingStatement(stmt);
    configureWhere(countingStatement, whereEls);

    return stmt;
  }

  void configureWhere(CountingStatement countingStatement, List<Element> whereEls) {
    for (Element where : whereEls) {
      where.configure(countingStatement);
    }
  }

  abstract Logger getLogger();
  abstract void makeOperation(StringBuilder sql, List<Join> joins);

  void makeKey(StringBuilder sql, Class<?> key) {
    Collection<Element> els = getElements(key);

    for (Element el : els) {
      sql.append(el.toString());
    }
  }

  Collection<Element> getElements(Class<?> keyClass) {
    return sql.getElements(keyClass);
  }

  private List<Join> getJoins() {
    List<Join> joins = newArrayList();
    recurseJoins(joins, this.sql);
    return joins;
  }

  private List<Element> getWhereEls(List<Join> joins) {
    Collection<Element> thisWheres = getElements(Where.class);
    thisWheres = thisWheres != null ? thisWheres : ImmutableList.<Element> of();

    Iterator<Iterator<Element>> iterOfWhereIter;
    iterOfWhereIter = Iterators.transform(joins.iterator(), new ToWhere());
    Iterator<Element> whereIter = Iterators.concat(iterOfWhereIter);
    List<Element> joinWheres = ImmutableList.copyOf(whereIter);

    return ImmutableList.copyOf(Iterables.concat(thisWheres, joinWheres));
  }

  private void makeWhere(StringBuilder sql, List<Element> whereEls) {
    Collection<Element> filtered = Collections2.filter(whereEls, new ActiveWhere());
    Collection<String> where = Collections2.transform(filtered, Functions.toStringFunction());

    if (!where.isEmpty()) {
      sql.append("where ");
      String _where = Joiner.on(" and " + New.Line).join(where);
      sql.append(_where);
      sql.append(" " + New.Line);
    }
  }

  private void recurseJoins(List<Join> cache, HasElements hasJoins) {
    Collection<Element> els = hasJoins.getElements(Join.class);
    if (els != null) {
      for (Element el : els) {
        Join join = (Join) el;
        cache.add(join);
        recurseJoins(cache, join);
      }
    }
  }

  private void makeOrderBy(StringBuilder sql) {
    Collection<String> orders = validateAndGetStrings(OrderProperty.class);
    if (!orders.isEmpty()) {
      sql.append("order by ");
      String _orders = Joiner.on(", " + New.Line).join(orders);
      sql.append(_orders);
      sql.append(" " + New.Line);
    }
  }

  private void limitIfPossible(StringBuilder sql) {
    makeKey(sql, Limit.class);
  }

  private Collection<String> validateAndGetStrings(Class<?> keyClass) {
    Collection<Element> els = sql.getElements(keyClass);
    return Collections2.transform(els, new ToString());
  }

  private class ToString implements Function<Element, String> {
    @Override
    public String apply(Element input) {
      return input.toString();
    }
  }

  private class ToWhere implements Function<HasElements, Iterator<Element>> {
    @Override
    public Iterator<Element> apply(HasElements input) {
      Collection<Element> wheres = input.getElements(Where.class);
      return wheres != null ? wheres.iterator() : Iterators.<Element> emptyIterator();
    }
  }

  private class ActiveWhere implements Predicate<Element> {
    @Override
    public boolean apply(Element input) {
      boolean apply = false;

      if (input instanceof Where) {
        Where where = (Where) input;
        apply = where.isAtivo();
      }

      return apply;
    }
  }

}