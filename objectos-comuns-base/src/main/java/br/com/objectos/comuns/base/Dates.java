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
package br.com.objectos.comuns.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public final class Dates {

  private static final long INITIAL_DATE = new GregorianCalendar(2001, Calendar.JANUARY, 1)
      .getTimeInMillis();

  private Dates() {
  }

  public static LocalDate newLocalDate(int year, int month, int day) {
    return new LocalDate(year, month, day);
  }

  public static XMLGregorianCalendar toXML(DateTime dateTime) {
    if (dateTime == null) {
      return null;
    }

    GregorianCalendar gregorianCalendar = dateTime.toGregorianCalendar();

    XMLGregorianCalendar calendar = datatypeFactory().newXMLGregorianCalendar(gregorianCalendar);

    return calendar;
  }

  public static XMLGregorianCalendar toXML(LocalDate localDate) {
    if (localDate == null) {
      return null;
    }

    XMLGregorianCalendar calendar = datatypeFactory().newXMLGregorianCalendar();

    calendar.setDay(localDate.getDayOfMonth());
    calendar.setMonth(localDate.getMonthOfYear());
    calendar.setYear(localDate.getYear());
    calendar.setHour(0);
    calendar.setMinute(0);
    calendar.setSecond(0);

    return calendar;
  }

  private static DatatypeFactory datatypeFactory() {
    try {
      return DatatypeFactory.newInstance();
    } catch (DatatypeConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  public static Date toDate(LocalDate localDate) {
    if (localDate == null) {
      return null;
    } else {
      return localDate.toDateTimeAtStartOfDay().toDate();
    }
  }

  public static Date toDate(XMLGregorianCalendar calendar) {
    if (calendar == null) {
      return null;
    } else {
      return calendar.toGregorianCalendar().getTime();
    }
  }

  public static DateTime asDateTime(Date date) {
    return date == null ? null : new DateTime(date);
  }

  public static DateTime asDateTime(XMLGregorianCalendar calendar) {
    return calendar == null ? null : new DateTime(calendar.toGregorianCalendar().getTime());
  }

  public static LocalDate asLocalDate(Date date) {
    return date == null ? null : new LocalDate(date);
  }

  public static LocalDate asLocalDate(XMLGregorianCalendar calendar) {
    return calendar == null ? null : new LocalDate(calendar.toGregorianCalendar().getTime());
  }

  public static XMLGregorianCalendar asXML(DateTime dateTime) throws DatatypeConfigurationException {
    GregorianCalendar gregorianCalendar = dateTime.toGregorianCalendar();
    XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(
        gregorianCalendar);
    return calendar;
  }

  public static XMLGregorianCalendar asXML(LocalDate localDate)
      throws DatatypeConfigurationException {
    XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    calendar.setDay(localDate.getDayOfMonth());
    calendar.setMonth(localDate.getMonthOfYear());
    calendar.setYear(localDate.getYear());
    calendar.setHour(0);
    calendar.setMinute(0);
    calendar.setSecond(0);
    return calendar;
  }

  public static LocalDate localDate(int year, int month, int day) {
    return new LocalDate(year, month, day);
  }

  public static int asInt(Date date) {
    Calendar aux = new GregorianCalendar();
    aux.setTime(date);

    int year = aux.get(Calendar.YEAR);
    int month = aux.get(Calendar.MONTH);
    int day = aux.get(Calendar.DAY_OF_MONTH);

    Calendar calendar = new GregorianCalendar(year, month, day);

    long diffMillis = calendar.getTimeInMillis() - INITIAL_DATE;

    int value = (int) (diffMillis / (24 * 60 * 60 * 1000));

    return value;
  }

  public static Date date(int year, int month, int day) {
    return toDate(localDate(year, month, day));
  }

  public static String asString(Date date) {
    DateFormat df = new SimpleDateFormat("yyyyMMdd");
    return df.format(date);
  }

  public static java.sql.Date asSql(Date date) {
    return new java.sql.Date(date.getTime());
  }

  public static Date normal(Date date) {
    Calendar aux = new GregorianCalendar();
    aux.setTime(date);

    int year = aux.get(Calendar.YEAR);
    int month = aux.get(Calendar.MONTH);
    int day = aux.get(Calendar.DAY_OF_MONTH);

    Calendar calendar = new GregorianCalendar(year, month, day);
    return calendar.getTime();
  }

  public static java.sql.Date instance(int year, int month, int day) {
    Calendar calendar = new GregorianCalendar(year, month - 1, day);
    return asSql(calendar.getTime());
  }

  public static int getMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.MONTH);
  }

  public static int getYear(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.YEAR);
  }

  public static LocalDate firstDayOfMonth(LocalDate localDate) {
    return localDate.dayOfMonth().withMinimumValue();
  }

  public static LocalDate firstDayOfYear(LocalDate localDate) {
    return localDate.dayOfYear().withMinimumValue();
  }

  public static LocalDate lastDayOfMonth(LocalDate localDate) {
    return localDate.dayOfMonth().withMaximumValue();
  }

  public static LocalDate lastDayOfNextMonth(LocalDate localDate) {
    LocalDate nextMonth = localDate.monthOfYear().addToCopy(1);
    return lastDayOfMonth(nextMonth);
  }

  public static LocalDate lastDayOfPreviousMonth(LocalDate localDate) {
    LocalDate previousMonth = localDate.monthOfYear().addToCopy(-1);
    return lastDayOfMonth(previousMonth);
  }

  public static LocalDate lastDayOfYear(LocalDate localDate) {
    return localDate.dayOfYear().withMaximumValue();
  }

}
