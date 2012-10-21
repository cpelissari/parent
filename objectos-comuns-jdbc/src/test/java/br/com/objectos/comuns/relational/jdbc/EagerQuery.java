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
import java.util.List;

import br.com.objectos.comuns.relational.search.ResultSetLoader;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class EagerQuery {

  private final Provider<NativeSql> sqlProvider;

  @Inject
  public EagerQuery(Provider<NativeSql> sqlProvider) {
    this.sqlProvider = sqlProvider;
  }

  public List<Eager> list() {
    return sqlProvider.get()

        .add("select *")

        .add("from COMUNS_RELATIONAL.EAGER as EAGER")
        .add("left join COMUNS_RELATIONAL.EAGER_MANY as EAGER_MANY")
        .add("on EAGER_MANY.EAGER_ID = EAGER.ID")

        .andLoadWith(new EagerLoader())

        .list();
  }

  private class EagerLoader implements ResultSetLoader<Eager> {

    private ResultSetWrapper rs;

    @Override
    public Eager load(ResultSet resultSet) throws SQLException {
      rs = new ResultSetWrapper(resultSet);

      int id = getId();
      String value = getValue();
      List<EagerMany> many = getMany();

      return new Eager(id, value, many);
    }

    private int getId() {
      return rs.getInt("EAGER.ID");
    }

    private String getValue() {
      return rs.getString("EAGER.VALUE");
    }

    private List<EagerMany> getMany() {
      final int id = getId();

      ResultSet resultSet = rs.getResultSet();
      return new EagerFetch<EagerMany>(resultSet)

          .andLoadWith(new EagerManyLoader())

          .onlyIf(new EagerFetch.Condition() {
            @Override
            public boolean isSatisfied() {
              rs.getInt("EAGER_MANY.ID");
              return !rs.wasNull();
            }
          })

          .whileTrue(new EagerFetch.Condition() {
            @Override
            public boolean isSatisfied() {
              return rs.getInt("EAGER.ID") == id;
            }
          });
    }

  }

  private class EagerManyLoader implements ResultSetLoader<EagerMany> {

    private ResultSetWrapper rs;

    @Override
    public EagerMany load(ResultSet resultSet) throws SQLException {
      rs = new ResultSetWrapper(resultSet);

      int id = getId();
      String many = getMany();

      return new EagerMany(id, many);
    }

    private int getId() {
      return rs.getInt("EAGER_MANY.ID");
    }

    private String getMany() {
      return rs.getString("EAGER_MANY.MANY");
    }

  }

}