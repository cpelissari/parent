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
package br.com.objectos.comuns.io;

import com.google.common.base.Supplier;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public interface Column {

  <T> T get(ColumnKey<T> key);
  <T> T get(Class<T> type);

  <T> T or(ColumnKey<T> key, T instance);
  <T> T or(Class<T> type, T instance);

  <T> T or(ColumnKey<T> key, Supplier<T> supplier);
  <T> T or(Class<T> type, Supplier<T> supplier);

  <T> T orNull(ColumnKey<T> key);
  <T> T orNull(Class<T> type);

}