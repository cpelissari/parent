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

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
class CrudGuice implements Crud {

  private final NativeSqlFactory sqlFactory;

  @Inject
  public CrudGuice(NativeSqlFactory sqlFactory) {
    this.sqlFactory = sqlFactory;
  }

  @Override
  public void create(EntityJdbc entity) {
    EntityMapping mapping = entity.toMapping();
    create(mapping);
  }
  @Override
  public void create(EntityMapping mapping) {
    Insertable insertable = mapping.toInsertable();
    sqlFactory.insert(insertable).insert();
  }

  @Override
  public void update(EntityJdbc entity) {
    EntityMapping mapping = entity.toMapping();
    update(mapping);
  }
  @Override
  public void update(EntityMapping mapping) {
    Updatable updatable = mapping.toUpdatable();
    sqlFactory.update(updatable).execute();
  }

  @Override
  public void delete(EntityJdbc entity) {
    EntityMapping mapping = entity.toMapping();
    delete(mapping);
  }
  @Override
  public void delete(EntityMapping mapping) {
    Deletable deletable = mapping.toDeletable();
    sqlFactory.delete(deletable).execute();
  }

}