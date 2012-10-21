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

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import br.com.objectos.comuns.relational.RelationalException;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class InsertOperationException extends RelationalException {

  private static final long serialVersionUID = 1L;

  public InsertOperationException(String message) {
    super(message);
  }

  public InsertOperationException(String message, Throwable cause) {
    super(message, cause);
  }

  public boolean isIntegrityViolation() {
    Throwable cause = getCause();
    List<Throwable> causes = Throwables.getCausalChain(cause);

    Iterable<SQLIntegrityConstraintViolationException> iter;
    iter = Iterables.filter(causes, SQLIntegrityConstraintViolationException.class);

    List<SQLIntegrityConstraintViolationException> integrity = ImmutableList.copyOf(iter);
    return !integrity.isEmpty();
  }

}