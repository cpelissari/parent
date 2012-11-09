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

import com.google.common.base.Preconditions;
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

  public AddTo addTo(Context context) {
    return new AddTo(context);
  }

  public abstract String get(String name);

  Context populate(Context context, Set<String> viewSet) {
    Map<String, String> views = newHashMap();

    @SuppressWarnings("unchecked")
    Map<String, String> ctxViews = (Map<String, String>) context.map.get("views");
    if (ctxViews != null) {
      views.putAll(ctxViews);
    }

    recurseViewMap(views, viewSet);

    context.put("views", views);

    return context;
  }

  private void recurseViewMap(Map<String, String> views, Set<String> viewSet) {
    for (String view : viewSet) {
      String html = views.get(view);

      if (html == null) {
        html = get(view);
        views.put(view, html);
      }

      Set<String> subTemplates = Tags.extractTemplates(html);
      if (!subTemplates.isEmpty()) {
        recurseViewMap(views, subTemplates);
      }
    }
  }

  public class AddTo {

    private final Context context;

    public AddTo(Context context) {
      this.context = context;
    }

    public AddToView as(String name) {
      Preconditions.checkNotNull(name);
      return new AddToView(context, name);
    }

  }

  public class AddToView {

    private final Context context;

    private final String key;

    public AddToView(Context context, String key) {
      this.context = context;
      this.key = key;
    }

    public void withSource(String html) {
      Map<String, String> views = newHashMap();
      views.put(key, html);

      Set<String> viewSet = Tags.extractTemplates(html);
      recurseViewMap(views, viewSet);

      @SuppressWarnings("unchecked")
      Map<String, String> ctxViews = (Map<String, String>) context.map.get("views");
      if (ctxViews != null) {
        views.putAll(ctxViews);
      }

      context.put("views", views);
    }

  }

}