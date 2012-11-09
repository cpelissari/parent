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


import com.google.inject.AbstractModule;
import com.google.inject.ImplementedBy;
import com.google.inject.Module;
import com.google.sitebricks.headless.Reply;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@ImplementedBy(PagesGuice.class)
public abstract class Pages {

  public static Module atPackage(final String pkgName) {
    return new AbstractModule() {
      @Override
      protected void configure() {
        bind(PagesBaseDir.class).toInstance(PagesBaseDir.atPackage(pkgName));
      }
    };
  }

  public abstract Reply<?> get(Class<?> templateClass, Context context);

  public abstract Reply<?> post(Class<?> templateClass, Context context);

}