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
package br.com.objectos.way.view;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
class ViewsGuice extends Views {

  private final LoadingCache<String, String> cache;

  private final ViewsBaseDir baseDir;

  @Inject
  public ViewsGuice(ViewsBaseDir baseDir) {
    this.cache = newCacheBuilder()
        .build(new ViewLoader());
    this.baseDir = baseDir;
  }

  @Override
  public String get(String name) {
    return cache.getUnchecked(name);
  }

  protected CacheBuilder<Object, Object> newCacheBuilder() {
    return CacheBuilder.newBuilder();
  }

  private class ViewLoader extends CacheLoader<String, String> {
    @Override
    public String load(String key) throws Exception {
      return baseDir.get(key);
    }
  }

}