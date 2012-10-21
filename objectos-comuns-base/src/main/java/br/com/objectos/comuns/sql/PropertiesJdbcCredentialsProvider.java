/*
 * Copyright 2011 Objectos, Fábrica de Software LTDA.
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
package br.com.objectos.comuns.sql;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.google.common.base.Throwables;
import com.google.common.io.InputSupplier;
import com.google.common.io.Resources;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class PropertiesJdbcCredentialsProvider implements Provider<JdbcCredentials> {

  private final Class<?> contextClass;

  public PropertiesJdbcCredentialsProvider(Class<?> contextClass) {
    this.contextClass = contextClass;
  }

  @Override
  public JdbcCredentials get() {
    try {

      URL url = Resources.getResource(contextClass, "/jdbc.properties");
      InputSupplier<InputStream> inputSupplier = Resources.newInputStreamSupplier(url);
      InputStream input = inputSupplier.getInput();
      Properties properties = new Properties();
      properties.load(input);

      return new JdbcCredentialsBuilder()//
          .driverClass(properties.getProperty("jdbc.driver")) //
          .url(properties.getProperty("jdbc.url")) //
          .user(properties.getProperty("jdbc.user")) //
          .password(properties.getProperty("jdbc.password")) //
          .get();

    } catch (IOException e) {

      throw Throwables.propagate(e);

    }
  }

}