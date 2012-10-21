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
class ResultSetWrapperPrefixLess implements ResultSetWrapperDelegate {

  private final ResultSet rs;

  public ResultSetWrapperPrefixLess(ResultSet rs) {
    this.rs = rs;
  }

  @Override
  public ResultSet getResultSet() {
    return rs;
  }

  @Override
  public String getString(String columnLabel) {
    try {
      return rs.getString(columnLabel);
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public boolean getBoolean(String columnLabel) {
    try {
      return rs.getBoolean(columnLabel);
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public byte getByte(String columnLabel) {
    try {
      return rs.getByte(columnLabel);
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public short getShort(String columnLabel) {
    try {
      return rs.getShort(columnLabel);
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public int getInt(String columnLabel) {
    try {
      return rs.getInt(columnLabel);
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public long getLong(String columnLabel) {
    try {
      return rs.getLong(columnLabel);
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
  public double getDouble(String columnLabel) {
    try {
      return rs.getDouble(columnLabel);
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public byte[] getBytes(String columnLabel) {
    try {
      return rs.getBytes(columnLabel);
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
      return rs.getDate(columnLabel);
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  @Override
  public DateTime getDateTime(String columnLabel) {
    try {
      Timestamp timestamp = rs.getTimestamp(columnLabel);
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
      return rs.getBigDecimal(columnLabel);
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