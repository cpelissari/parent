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
class ResultSetWrapperPrefix implements ResultSetWrapperDelegate {

  private final String prefix;

  private final ResultSet rs;

  public ResultSetWrapperPrefix(String prefix, ResultSet rs) {
    this.prefix = prefix;
    this.rs = rs;
  }

  public String getPrefix() {
    return prefix;
  }

  @Override
  public ResultSet getResultSet() {
    return rs;
  }

  private String col(String columnLabel) {
    return prefix + "." + columnLabel;
  }

  @Override
  public String getString(String columnLabel) {
    try {
      return rs.getString(col(columnLabel));
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public boolean getBoolean(String columnLabel) {
    try {
      return rs.getBoolean(col(columnLabel));
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public byte getByte(String columnLabel) {
    try {
      return rs.getByte(col(columnLabel));
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public short getShort(String columnLabel) {
    try {
      return rs.getShort(col(columnLabel));
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public int getInt(String columnLabel) {
    try {
      return rs.getInt(col(columnLabel));
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public float getFloat(String columnLabel) {
    try {
      return rs.getFloat(columnLabel);
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public long getLong(String columnLabel) {
    try {
      return rs.getLong(col(columnLabel));
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public double getDouble(String columnLabel) {
    try {
      return rs.getDouble(col(columnLabel));
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public byte[] getBytes(String columnLabel) {
    try {
      return rs.getBytes(col(columnLabel));
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public LocalDate getLocalDate(String columnLabel) {
    Date date = getDate(columnLabel);
    return date != null ? new LocalDate(date) : null;
  }

  @Override
  public Date getDate(String columnLabel) {
    try {
      return rs.getDate(col(columnLabel));
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public DateTime getDateTime(String columnLabel) {
    try {
      Timestamp timestamp = rs.getTimestamp(col(columnLabel));
      return new DateTime(timestamp);
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public Time getTime(String columnLabel) throws SQLException {
    return rs.getTime(columnLabel);
  }

  @Override
  public Timestamp getTimestamp(String columnLabel) throws SQLException {
    return rs.getTimestamp(columnLabel);
  }

  @Override
  public Object getObject(String columnLabel) throws SQLException {
    return rs.getObject(columnLabel);
  }

  @Override
  public Reader getCharacterStream(String columnLabel) throws SQLException {
    return rs.getCharacterStream(columnLabel);
  }

  @Override
  public BigDecimal getBigDecimal(String columnLabel) {
    try {
      return rs.getBigDecimal(col(columnLabel));
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public Clob getClob(String columnLabel) throws SQLException {
    return rs.getClob(columnLabel);
  }

  @Override
  public Array getArray(String columnLabel) throws SQLException {
    return rs.getArray(columnLabel);
  }

  @Override
  public NClob getNClob(String columnLabel) throws SQLException {
    return rs.getNClob(columnLabel);
  }

  @Override
  public String getNString(String columnLabel) throws SQLException {
    return rs.getNString(columnLabel);
  }

  @Override
  public boolean wasNull() {
    try {
      return rs.wasNull();
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

}