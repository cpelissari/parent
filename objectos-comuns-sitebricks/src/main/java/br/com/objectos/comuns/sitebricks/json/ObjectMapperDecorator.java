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
package br.com.objectos.comuns.sitebricks.json;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.VisibilityChecker;
import org.codehaus.jackson.map.module.SimpleModule;
import org.joda.time.LocalDate;

import br.com.objectos.comuns.base.br.Cep;
import br.com.objectos.comuns.base.br.Cnpj;
import br.com.objectos.comuns.base.br.Cpf;
import br.com.objectos.comuns.matematica.financeira.Percentual;
import br.com.objectos.comuns.matematica.financeira.ValorFinanceiro;
import br.com.objectos.comuns.sitebricks.BaseUrl;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ObjectMapperDecorator {

  private final BaseUrl baseUrl;

  public ObjectMapperDecorator(BaseUrl baseUrl) {
    this.baseUrl = baseUrl;
  }

  public ObjectMapper decorate(ObjectMapper mapper) {
    SerializationConfig config = mapper.getSerializationConfig();
    VisibilityChecker<?> checker = config.getDefaultVisibilityChecker();

    mapper.setVisibilityChecker(
        checker
            .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
            .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
            .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
            .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
        );

    mapper.registerModule(new CustomModule(mapper));

    return mapper;
  }

  private class CustomModule extends SimpleModule {

    public CustomModule(ObjectMapper mapper) {
      super("CustomModule", new Version(0, 1, 1, "duh"));

      // objectos comuns base
      addSerializer(Cep.class, new CepSerializer());
      addSerializer(Cnpj.class, new CnpjSerializer());
      addSerializer(Context.class, new ContextSerializer(mapper));
      addSerializer(Cpf.class, new CpfSerializer());
      addSerializer(Url.class, new UrlSerializer(baseUrl));

      // objectos comuns base (fin)
      addSerializer(Percentual.class, new PercentualSerializer());
      addSerializer(ValorFinanceiro.class, new ValorFinanceiroSerializer());

      // joda
      addSerializer(LocalDate.class, new LocalDateSerializer());
    }

  }

}