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

import br.com.objectos.comuns.relational.UniqueInsert;

import com.google.common.base.Supplier;
import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class UniqueInsertJdbc implements UniqueInsert {

  private final AtomicInsert atomicInsert;

  @Inject
  public UniqueInsertJdbc(AtomicInsert atomicInsert) {
    this.atomicInsert = atomicInsert;
  }

  @Override
  public <T> T of(T entity, Supplier<T> finder) {
    try {
      atomicInsert.of(entity);
      return entity;
    } catch (InsertOperationException e) {

      if (e.isIntegrityViolation()) {

        T existing = finder.get();

        if (existing == null) {
          throw new UniqueInsertOperationException("Tried to return found entity but got null", e);
        }

        if (!entity.equals(existing)) {
          throw new UniqueInsertOperationException("Found entity is not equal to supplied entity. "
              + "Maybe you forgot to implement equals() and hashCode()", e);
        }

        return existing;

      } else {

        throw e;

      }

    }
  }

}