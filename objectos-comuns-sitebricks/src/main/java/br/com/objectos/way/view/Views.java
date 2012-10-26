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

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;
import java.util.Set;

import br.com.objectos.comuns.sitebricks.json.Context;

import com.google.inject.AbstractModule;
import com.google.inject.ImplementedBy;
import com.google.inject.Module;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@ImplementedBy(ViewsGuice.class)
public abstract class Views {

  public static Module atPackage(final String pkgName) {
    return new AbstractModule() {
      @Override
      protected void configure() {
        bind(ViewsBaseDir.class).toInstance(ViewsBaseDir.atPackage(pkgName));
      }
    };
  }

  public abstract String get(String name);

  Context populate(Context context, Set<String> viewSet) {
    Map<Object, Object> views = newHashMap();

    recurseViewMap(views, viewSet);

    context.put("views", views);

    return context;
  }

  private void recurseViewMap(Map<Object, Object> views, Set<String> viewSet) {
    for (String view : viewSet) {
      String html = get(view);
      views.put(view, html);

      Set<String> subTemplates = Tags.extractTemplates(html);
      if (!subTemplates.isEmpty()) {
        recurseViewMap(views, subTemplates);
      }
    }
  }

}