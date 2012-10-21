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
package br.com.objectos.comuns.base.cache;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractCacheBuilder implements CacheBuilder {

  @Override
  public com.google.common.cache.CacheBuilder<Object, Object> small() {
    return maximumSize(25);
  }

  @Override
  public com.google.common.cache.CacheBuilder<Object, Object> medium() {
    return maximumSize(200);
  }

  @Override
  public com.google.common.cache.CacheBuilder<Object, Object> large() {
    return maximumSize(500);
  }

  @Override
  public com.google.common.cache.CacheBuilder<Object, Object> maximumSize(long size) {
    double factor = getMaximumSizeFactor();

    return newBuilder()
        .maximumSize((long) (size * factor));
  }

  protected double getMaximumSizeFactor() {
    return 1d;
  }

}