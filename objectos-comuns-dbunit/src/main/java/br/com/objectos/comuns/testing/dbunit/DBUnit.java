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

import static com.google.common.collect.Lists.asList;

import java.sql.SQLException;
import java.util.List;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
public class DBUnit {

  public static final String DATATYPE_FACTORY = "http://www.dbunit.org/properties/datatypeFactory";
  public static final String ESCAPE_PATTERN = "http://www.dbunit.org/properties/escapePattern";
  public static final String METADATA_HANDLER = "http://www.dbunit.org/properties/metadataHandler";
  public static final String QUALIFIED_TABLE_NAMES = "http://www.dbunit.org/features/qualifiedTableNames";

  private final Provider<IDatabaseConnection> connections;

  private DefaultDataSupplierSet defaultSet = new EmptyDefaultDataSupplierSet();

  @Inject
  DBUnit(Provider<IDatabaseConnection> connections) {
    this.connections = connections;
  }

  @Inject(optional = true)
  void setDefaultSet(DefaultDataSupplierSet defaultSet) {
    this.defaultSet = defaultSet;
  }

  public void loadDefaultDataSet() {
    List<DataSupplier> datasets = defaultSet.get();
    execute(datasets);
  }

  public void load(DataSupplier supplier) {
    List<DataSupplier> arquivos = ImmutableList.of(supplier);
    execute(arquivos);
  }

  public void load(DataSupplier first, DataSupplier... more) {
    List<DataSupplier> arquivos = asList(first, more);
    execute(arquivos);
  }

  private void execute(List<DataSupplier> datasets) {
    tryToExecute(datasets);
  }

  private void tryToExecute(List<DataSupplier> datasets) {

    IDatabaseConnection conn = connections.get(); // don't get conned

    try {
      disableReferentialIntegrity(conn);

      for (DataSupplier op : datasets) {

        try {

          IDataSet dataSet = op.get();

          DatabaseOperation operacao = op.getOperation();
          DatabaseOperation trx = DatabaseOperation.TRANSACTION(operacao);
          trx.execute(conn, dataSet);

        } catch (Exception e) {

          Throwables.propagateIfInstanceOf(e, DBUnitSetupException.class);
          String msg = String.format("Could not load DBUnit file: %s", op.getFilename());
          throw new DBUnitSetupException(msg, e);

        }
      }

      enableReferentialIntegrity(conn);
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        // move along, nothing to see here
      }
    }

  }

  protected void disableReferentialIntegrity(IDatabaseConnection con) {
    justDoIt(con, "set referential_integrity FALSE"); // HSQL DB 1.8
    justDoIt(con, "SET DATABASE REFERENTIAL INTEGRITY FALSE"); // HSQL DB 2.0
    justDoIt(con, "set foreign_key_checks=0"); // MySQL
  }

  protected void enableReferentialIntegrity(IDatabaseConnection con) {
    justDoIt(con, "set referential_integrity TRUE"); // HSQL DB 1.8
    justDoIt(con, "SET DATABASE REFERENTIAL INTEGRITY TRUE"); // HSQL DB 2.0
    justDoIt(con, "set foreign_key_checks=1"); // MySQL
  }

  private void justDoIt(IDatabaseConnection con, String sql) {
    try {
      con.getConnection().prepareStatement(sql).execute();
    } catch (Exception ex) {
      // yep... don't complain. We (think we) know what we're doing.
    }
  }

}