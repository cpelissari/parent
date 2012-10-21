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

import java.sql.SQLException;
import java.util.List;

import br.com.objectos.comuns.relational.jdbc.Transactions.AtomicInsertOperation;

import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ListInsertableExecGuice implements ListInsertableExec {

  private final AtomicInsert atomicInsert;

  @Inject
  public ListInsertableExecGuice(AtomicInsert atomicInsert) {
    this.atomicInsert = atomicInsert;
  }

  @Override
  public void execute(ListInsertable insertable) {
    final List<Insert> inserts = insertable.getInserts();

    atomicInsert.of(new AtomicInsertOperation() {
      @Override
      public void execute(Insertion insertion) throws SQLException {
        for (Insert insert : inserts) {
          insertion.of(insert);
        }
      }
    });
  }

}