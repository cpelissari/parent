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
package br.com.objectos.comuns.sitebricks;

import com.google.common.collect.ImmutableSet;
import com.google.inject.AbstractModule;
import com.google.sitebricks.conversion.Converter;
import com.google.sitebricks.conversion.ConverterRegistry;
import com.google.sitebricks.conversion.StandardTypeConverter;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ObjectosComunsSitebricksTestModule extends AbstractModule {

  @SuppressWarnings("rawtypes")
  @Override
  protected void configure() {
    install(new ObjectosComunsSitebricksModule());

    bind(BaseUrl.class).to(FakeBaseUrl.class);
    bind(ConverterRegistry.class).toInstance(
        new StandardTypeConverter(ImmutableSet.<Converter> of()));
  }

  private static class FakeBaseUrl implements BaseUrl {
    @Override
    public String get() {
      return "http://fake";
    }
  }

}