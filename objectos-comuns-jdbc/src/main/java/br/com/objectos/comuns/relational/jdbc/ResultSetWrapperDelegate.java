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

import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
interface ResultSetWrapperDelegate {

  ResultSet getResultSet();

  String getString(String columnLabel);

  boolean getBoolean(String columnLabel);

  byte getByte(String columnLabel);

  short getShort(String columnLabel);

  int getInt(String columnLabel);

  long getLong(String columnLabel);

  float getFloat(String columnLabel);

  double getDouble(String columnLabel);

  byte[] getBytes(String columnLabel);

  LocalDate getLocalDate(String columnLabel);

  Date getDate(String columnLabel);

  DateTime getDateTime(String columnLabel);

  Time getTime(String columnLabel) throws SQLException;

  Timestamp getTimestamp(String columnLabel) throws SQLException;

  Object getObject(String columnLabel) throws SQLException;

  Reader getCharacterStream(String columnLabel) throws SQLException;

  BigDecimal getBigDecimal(String columnLabel);

  Clob getClob(String columnLabel) throws SQLException;

  Array getArray(String columnLabel) throws SQLException;

  NClob getNClob(String columnLabel) throws SQLException;

  String getNString(String columnLabel) throws SQLException;

  boolean wasNull();

}