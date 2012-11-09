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

import com.github.mustachejava.MustacheFactory;
import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class DevelopmentMustaches extends MustachesGuice {

  @Inject
  public DevelopmentMustaches(PagesBaseDir pagesBaseDir) {
    super(pagesBaseDir);
  }

  @Override
  MustacheFactory newMustacheFactory(PagesBaseDir pagesBaseDir) {
    String resourceRoot = pagesBaseDir.getBaseDir();
    return new DevelopmentMustacheFactory(resourceRoot);
  }

}