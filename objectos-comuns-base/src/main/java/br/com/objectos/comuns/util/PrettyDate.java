/*
 * PrettyDate.java criado em 08/02/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.util;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class PrettyDate {

  private static final PeriodFormatter SECONDS = new PeriodFormatterBuilder()
      .appendSeconds()
      .appendSuffix(" seg. atrás")
      .toFormatter();

  private static final PeriodFormatter MINUTES = new PeriodFormatterBuilder()
      .appendMinutes()
      .appendSuffix(" min. atrás")
      .toFormatter();

  private static final PeriodFormatter HOURS = new PeriodFormatterBuilder()
      .appendHours()
      .appendSuffix(" hora(s) atrás")
      .toFormatter();

  private static final PeriodFormatter DAYS = new PeriodFormatterBuilder()
      .appendDays()
      .appendSuffix(" dia(s) atrás")
      .toFormatter();

  private static final PeriodFormatter WEEKS = new PeriodFormatterBuilder()
      .appendWeeks()
      .appendSuffix(" sem. atrás")
      .toFormatter();

  private static final PeriodFormatter MONTHS = new PeriodFormatterBuilder()
      .appendMonths()
      .appendSuffix(" mês(es) atrás")
      .toFormatter();

  public static String print(DateTime dateTime) {
    Duration duration = new Duration(dateTime, null);

    PeriodFormatter formatter = MONTHS;
    if (duration.isShorterThan(Duration.standardMinutes(1))) {
      formatter = SECONDS;
    }

    else if (duration.isShorterThan(Duration.standardHours(1))) {
      formatter = MINUTES;
    }

    else if (duration.isShorterThan(Duration.standardDays(1))) {
      formatter = HOURS;
    }

    else if (duration.isShorterThan(Duration.standardDays(7))) {
      formatter = DAYS;
    }

    else if (duration.isShorterThan(Duration.standardDays(30))) {
      formatter = WEEKS;
    }

    Period period = new Period(duration, new DateTime());
    return formatter.print(period);
  }

}