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
package br.com.objectos.comuns.testing.jdbc;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import br.com.objectos.comuns.relational.jdbc.Insertable;
import br.com.objectos.comuns.relational.jdbc.ListInsertable;
import br.com.objectos.comuns.relational.jdbc.NativeBatchInsert;
import br.com.objectos.comuns.relational.jdbc.NativeSqlFactory;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
class SqlUnitGuice implements SqlUnit {

  private final Injector injector;

  private final NativeBatchInsert batchInsert;
  private final NativeSqlFactory sqlFactory;
  private final Truncate truncate;

  @Inject
  public SqlUnitGuice(Injector injector,
                      NativeBatchInsert batchInsert,
                      NativeSqlFactory sqlFactory,
                      Truncate truncate) {
    this.injector = injector;
    this.batchInsert = batchInsert;
    this.sqlFactory = sqlFactory;
    this.truncate = truncate;
  }

  @Override
  public void loadEntitySet(Class<? extends EntitySet> entitySetClass) {
    EntitySet entitySet = injector.getInstance(entitySetClass);

    entitySet.truncate(truncate);
    entitySet.load(this);
  }

  @Override
  public void truncate(Class<? extends EntitySet> entitySetClass) {
    EntitySet entitySet = injector.getInstance(entitySetClass);

    entitySet.truncate(truncate);
  }

  @Override
  public void batchInsert(Iterable<? extends Insertable> entities) {
    batchInsert.insert(entities);
  }

  @Override
  public void batchInsertMany(Iterable<? extends ListInsertable> entities) {
    batchInsert.insertMany(entities);
  }

  @Override
  public AddInsertable add(final Insertable entity) {
    return new AddInsertable() {

      private final List<Insertable> entities = newArrayList(entity);

      @Override
      public void insert() {
        for (Insertable entity : entities) {
          sqlFactory.insert(entity).insert();
        }
      }

      @Override
      public void batchInsert() {
        batchInsert.insert(entities);
      }

      @Override
      public AddInsertable add(Insertable entity) {
        entities.add(entity);
        return this;
      }

      @Override
      public AddInsertable addAll(Iterable<? extends Insertable> entities) {
        List<Insertable> list = ImmutableList.copyOf(entities);
        this.entities.addAll(list);
        return this;
      }

    };
  }

  @Override
  public AddListInsertable add(final ListInsertable entity) {
    return new AddListInsertable() {

      private final List<ListInsertable> entities = newArrayList(entity);

      @Override
      public void insert() {
        for (ListInsertable entity : entities) {
          sqlFactory.insertMany(entity).insert();
        }
      }

      @Override
      public void batchInsert() {
        batchInsert.insertMany(entities);
      }

      @Override
      public AddListInsertable add(ListInsertable entity) {
        entities.add(entity);
        return this;
      }

      @Override
      public AddListInsertable addAll(Iterable<? extends ListInsertable> entities) {
        List<ListInsertable> list = ImmutableList.copyOf(entities);
        this.entities.addAll(list);
        return this;
      }

    };
  }

}