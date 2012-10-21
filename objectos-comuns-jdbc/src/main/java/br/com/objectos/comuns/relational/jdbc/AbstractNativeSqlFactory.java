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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
abstract class AbstractNativeSqlFactory implements NativeSqlFactory {

  private final ListInsertableExec listInsertableExec;

  public AbstractNativeSqlFactory(ListInsertableExec listInsertableExec) {
    this.listInsertableExec = listInsertableExec;
  }

  @Override
  public FluentExecute delete(Deletable deletable) {
    final Delete delete = deletable.getDelete();

    return new FluentExecute() {
      @Override
      public int execute() {
        return create(delete).execute();
      }
    };
  }

  @Override
  public FluentInsert insert(Insertable insertable) {
    final Insert insert = insertable.getInsert();

    return new FluentInsert() {
      @Override
      public void insert() {
        create(insert)
            .onGeneratedKey(insert.getKeyCallback())
            .insert();
      }
    };
  }

  @Override
  public FluentInsert insertMany(final ListInsertable insertable) {
    return new FluentInsert() {
      @Override
      public void insert() {
        listInsertableExec.execute(insertable);
      }
    };
  }

  @Override
  public FluentExecute update(Updatable updateable) {
    final Update update = updateable.getUpdate();

    return new FluentExecute() {
      @Override
      public int execute() {
        return create(update).execute();
      }
    };
  }

}