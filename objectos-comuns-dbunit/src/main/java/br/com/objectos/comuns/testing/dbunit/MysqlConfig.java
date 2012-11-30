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
package br.com.objectos.comuns.testing.dbunit;

import java.util.Set;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class MysqlConfig implements VendorConfig {

  static final Logger logger = LoggerFactory.getLogger(MysqlConfig.class);

  private final Set<String> catalogs;

  public MysqlConfig(Set<String> catalogs) {
    this.catalogs = catalogs;
  }

  @Override
  public void configure(IDatabaseConnection connection) {
    DatabaseConfig config = connection.getConfig();

    config.setProperty(DBUnit.DATATYPE_FACTORY, new MySqlDataTypeFactory());

    config.setProperty(DBUnit.ESCAPE_PATTERN, "`?`");

    config.setProperty(DBUnit.METADATA_HANDLER, new MySqlMetadataHandler(catalogs));

    config.setProperty(DBUnit.QUALIFIED_TABLE_NAMES, true);
  }

}