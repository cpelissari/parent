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
public class ResultSetWrapper implements ResultSetWrapperDelegate {

  private final String prefix;

  private final ResultSetWrapperDelegate delegate;

  public ResultSetWrapper(ResultSet resultSet) {
    this.prefix = null;
    this.delegate = new ResultSetWrapperPrefixLess(resultSet);
  }

  public ResultSetWrapper(String prefix, ResultSet resultSet) {
    this.prefix = prefix;
    this.delegate = new ResultSetWrapperPrefix(prefix, resultSet);
  }

  public String getPrefix() {
    return prefix;
  }

  @Override
  public ResultSet getResultSet() {
    return delegate.getResultSet();
  }

  @Override
  public String getString(String columnLabel) {
    return delegate.getString(columnLabel);
  }

  @Override
  public boolean getBoolean(String columnLabel) {
    return delegate.getBoolean(columnLabel);
  }

  @Override
  public byte getByte(String columnLabel) {
    return delegate.getByte(columnLabel);
  }

  @Override
  public short getShort(String columnLabel) {
    return delegate.getShort(columnLabel);
  }

  @Override
  public int getInt(String columnLabel) {
    return delegate.getInt(columnLabel);
  }

  @Override
  public long getLong(String columnLabel) {
    return delegate.getLong(columnLabel);
  }

  @Override
  public float getFloat(String columnLabel) {
    return delegate.getFloat(columnLabel);
  }

  @Override
  public double getDouble(String columnLabel) {
    return delegate.getDouble(columnLabel);
  }

  @Override
  public byte[] getBytes(String columnLabel) {
    return delegate.getBytes(columnLabel);
  }

  @Override
  public LocalDate getLocalDate(String columnLabel) {
    return delegate.getLocalDate(columnLabel);
  }

  @Override
  public Date getDate(String columnLabel) {
    return delegate.getDate(columnLabel);
  }

  @Override
  public DateTime getDateTime(String columnLabel) {
    return delegate.getDateTime(columnLabel);
  }

  @Override
  public Time getTime(String columnLabel) throws SQLException {
    return delegate.getTime(columnLabel);
  }

  @Override
  public Timestamp getTimestamp(String columnLabel) throws SQLException {
    return delegate.getTimestamp(columnLabel);
  }

  @Override
  public Object getObject(String columnLabel) throws SQLException {
    return delegate.getObject(columnLabel);
  }

  @Override
  public Reader getCharacterStream(String columnLabel) throws SQLException {
    return delegate.getCharacterStream(columnLabel);
  }

  @Override
  public BigDecimal getBigDecimal(String columnLabel) {
    return delegate.getBigDecimal(columnLabel);
  }

  @Override
  public Clob getClob(String columnLabel) throws SQLException {
    return delegate.getClob(columnLabel);
  }

  @Override
  public Array getArray(String columnLabel) throws SQLException {
    return delegate.getArray(columnLabel);
  }

  @Override
  public NClob getNClob(String columnLabel) throws SQLException {
    return delegate.getNClob(columnLabel);
  }

  @Override
  public String getNString(String columnLabel) throws SQLException {
    return delegate.getNString(columnLabel);
  }

  @Override
  public boolean wasNull() {
    return delegate.wasNull();
  }

}