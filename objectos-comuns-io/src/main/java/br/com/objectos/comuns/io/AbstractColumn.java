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
package br.com.objectos.comuns.io;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import com.google.common.base.Supplier;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractColumn<V> implements Column {

  private final Map<ColumnKey<?>, ? extends ColumnConverter<V, ?>> converterMap;

  private final boolean nullOrEmpty;

  private final V value;

  public AbstractColumn(Map<ColumnKey<?>, ? extends ColumnConverter<V, ?>> converterMap, V value) {
    this.converterMap = converterMap;
    this.nullOrEmpty = isNullOrEmpty(value);
    this.value = cleanValue(value);
  }

  protected abstract boolean isNullOrEmpty(V value);

  protected abstract V cleanValue(V value);

  @Override
  public <T> T get(ColumnKey<T> key) {
    checkKey(key);
    checkNullOrEmpty(key);

    return convert(key);
  }

  @Override
  public <T> T get(Class<T> type) {
    return get(ColumnKey.of(type));
  }

  @Override
  public <T> T or(ColumnKey<T> key, T instance) {
    checkKey(key);

    return nullOrEmpty ? instance : convert(key);
  }

  @Override
  public <T> T or(Class<T> type, T instance) {
    return or(ColumnKey.of(type), instance);
  }

  @Override
  public <T> T or(ColumnKey<T> key, Supplier<T> supplier) {
    checkKey(key);

    return nullOrEmpty ? supplier.get() : convert(key);
  }

  @Override
  public <T> T or(Class<T> type, Supplier<T> supplier) {
    return or(ColumnKey.of(type), supplier);
  }

  @Override
  public <T> T orNull(ColumnKey<T> key) {
    checkKey(key);

    return nullOrEmpty ? null : convert(key);
  }

  @Override
  public <T> T orNull(Class<T> type) {
    return orNull(ColumnKey.of(type));
  }

  @SuppressWarnings("unchecked")
  private <T> T convert(ColumnKey<T> key) {
    ColumnConverter<V, ?> converter = converterMap.get(key);
    return (T) converter.apply(value);
  }

  private void checkKey(ColumnKey<?> key) {
    checkNotNull(key, "key");

    if (!converterMap.containsKey(key)) {
      throw new IllegalArgumentException("No ColumnConverter bound to key: " + key);
    }
  }

  private void checkNullOrEmpty(ColumnKey<?> key) {
    if (nullOrEmpty) {
      throw new ColumnConversionException(null, key.getType());
    }
  }

}