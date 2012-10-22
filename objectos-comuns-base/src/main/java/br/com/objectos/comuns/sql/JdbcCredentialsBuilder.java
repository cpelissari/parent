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

import br.com.objectos.comuns.base.AbstractCredentialsBuilder;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class JdbcCredentialsBuilder extends
    AbstractCredentialsBuilder<JdbcCredentials, JdbcCredentialsBuilder> {

  private String driverClass;
  private String url;

  @Override
  public JdbcCredentials get() {
    Preconditions.checkNotNull(driverClass);
    Preconditions.checkNotNull(url);
    Preconditions.checkNotNull(user);
    Preconditions.checkNotNull(password);

    return new JdbcCredentials() {
      @Override
      public String getUser() {
        return user;
      }

      @Override
      public String getPassword() {
        return password;
      }

      @Override
      public String getUrl() {
        return url;
      }

      @Override
      public String getDriverClass() {
        return driverClass;
      }
    };
  }

  public JdbcCredentialsBuilder driverClass(String driverClass) {
    this.driverClass = driverClass;
    return self();
  }

  public JdbcCredentialsBuilder url(String url) {
    this.url = url;
    return self();
  }

  @Override
  protected JdbcCredentialsBuilder self() {
    return this;
  }

}