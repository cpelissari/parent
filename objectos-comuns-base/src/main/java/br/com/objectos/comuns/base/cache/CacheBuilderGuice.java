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

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class CacheBuilderGuice extends AbstractCacheBuilder {

  private final double gigabytes;

  public CacheBuilderGuice() {
    this(1);
  }

  public CacheBuilderGuice(double gigabytes) {
    Preconditions.checkArgument(gigabytes >= 1, "Enter the expected JVM GBs available");
    this.gigabytes = gigabytes;
  }

  @Override
  public com.google.common.cache.CacheBuilder<Object, Object> newBuilder() {
    return com.google.common.cache.CacheBuilder.newBuilder();
  }

  @Override
  protected double getMaximumSizeFactor() {
    return gigabytes;
  }

}