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
package br.com.objectos.comuns.relational.jdbc;

import java.sql.Connection;

import br.com.objectos.comuns.relational.BatchInsert;
import br.com.objectos.comuns.relational.search.SearchQueryExec;
import br.com.objectos.comuns.sql.JdbcCredentials;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.throwingproviders.ThrowingProviderBinder;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class RelationalJdbcModuleBuilder {

  private Vendor vendor = Vendor.ANSI;

  public WithC3P0Builder withC3P0(Provider<JdbcCredentials> credentials) {
    return new WithC3P0Builder(credentials);
  }

  public class WithC3P0Builder {

    private final Provider<JdbcCredentials> credentials;

    private Class<? extends Provider<ComboPooledDataSource>> poolProvider = C3P0PoolProvider.class;

    private boolean jpa = false;

    public WithC3P0Builder(Provider<JdbcCredentials> credentials) {
      this.credentials = credentials;
    }

    public WithC3P0Builder withJPACompatibility() {
      jpa = true;
      return this;
    }

    public WithC3P0Builder withMysql() {
      vendor = Vendor.MYSQL;
      return this;
    }

    public WithC3P0Builder poolProvider(
        Class<? extends Provider<ComboPooledDataSource>> poolProvider) {
      this.poolProvider = poolProvider;
      return this;
    }

    public Module build() {
      return new AbstractModule() {
        @Override
        protected void configure() {
          bind(ComboPooledDataSource.class)
              .toProvider(poolProvider)
              .in(Scopes.SINGLETON);

          bind(JdbcCredentials.class)
              .annotatedWith(C3P0.class)
              .toProvider(credentials);

          ThrowingProviderBinder.create(binder())
              .bind(SQLProvider.class, Connection.class)
              .to(C3P0ConnectionProvider.class);

          install(new RelationalJdbcBaseModule());
          install(vendor.getModule());

          if (!jpa) {
            bind(BatchInsert.class).to(AtomicInsert.class).in(Scopes.SINGLETON);
            bind(SearchQueryExec.class).to(JdbcSearchQueryExec.class).in(Scopes.SINGLETON);
          } else {
            bind(br.com.objectos.comuns.relational.jdbc.BatchInsert.class)
                .to(AtomicInsert.class)
                .in(Scopes.SINGLETON);
            bind(br.com.objectos.comuns.relational.jdbc.SearchQueryExec.class)
                .to(JdbcSearchQueryExec.class)
                .in(Scopes.SINGLETON);
          }
        }
      };
    }

  }

}