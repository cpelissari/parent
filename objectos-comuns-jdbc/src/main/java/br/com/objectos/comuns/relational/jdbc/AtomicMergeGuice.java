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

import java.sql.SQLException;

import br.com.objectos.comuns.relational.jdbc.Transactions.AtomicInsertOperation;
import br.com.objectos.comuns.relational.jdbc.Transactions.AtomicUpdateOperation;
import br.com.objectos.comuns.relational.jdbc.Transactions.Update;

import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class AtomicMergeGuice implements AtomicMerge {

  private final Transactions transactions;

  @Inject
  public AtomicMergeGuice(Transactions transactions) {
    this.transactions = transactions;
  }

  @Override
  public void insert(AtomicInsertOperation operation) {
    try {
      transactions.execute(operation);
    } catch (TransactionRolledbackException e) {
      throw new InsertOperationException("Could not insert one or more entities. "
          + "More info below.", e);
    }
  }

  @Override
  public <I extends Insertable> I insert(final I entity) {
    AtomicInsertOperation operation = new AtomicInsertOperation() {
      @Override
      public void execute(Insertion insertion) throws SQLException {
        insertion.of(entity);
      }
    };

    insert(operation);

    return entity;
  }

  @Override
  public <T extends Insertable> T update(AtomicUpdateOperation<T> operation) {
    try {
      return transactions.executeUpdate(operation);
    } catch (TransactionRolledbackException e) {
      throw new UpdateOperationException("Could not update one or more entities. "
          + "More info below.", e);
    }
  }

  @Override
  public <I extends Insertable> UpdateBuilder<I> update(final I entity) {
    return new UpdateBuilder<I>() {
      @Override
      public I with(final PrimaryKey primaryKey) {
        try {
          return transactions.executeUpdate(new Transactions.AtomicUpdateOperation<I>() {
            @Override
            public I execute(Update update) throws SQLException {
              update.of(primaryKey, entity);
              return entity;
            }
          });
        } catch (TransactionRolledbackException e) {
          throw new UpdateOperationException("Could not update one or more entities. "
              + "More info below.", e);
        }
      }
    };
  }

}