/*
 * DefaultRequestWrapper.java criado em 27/09/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.joda.time.LocalDate;

import br.com.objectos.comuns.base.text.DecimalFormats;
import br.com.objectos.comuns.matematica.financeira.Percentual;
import br.com.objectos.comuns.relational.search.Page;
import br.com.objectos.comuns.relational.search.SimplePage;
import br.com.objectos.comuns.sitebricks.relational.SearchString;

import com.google.common.base.Strings;
import com.google.sitebricks.headless.Request;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class DefaultRequestWrapper implements RequestWrapper {

  private final DecimalFormat doubleFormat = DecimalFormats.newCurrencyWithLocale("pt");

  private final Request req;

  public DefaultRequestWrapper(Request req) {
    this.req = req;
  }

  @Override
  public String param(String param) {
    return req.param(param);
  }

  @Override
  public boolean booleanParam(String param) {
    String value = req.param(param);
    return value != null;
  }

  @Override
  public <E extends Enum<E>> E enumParam(Class<E> enumClass, String param) {
    E res = null;

    String value = req.param(param);
    if (value != null) {
      try {
        res = Enum.valueOf(enumClass, value);
      } catch (IllegalArgumentException e) {

      }
    }

    return res;
  }

  @Override
  public LocalDate localDateParam(String param) {
    return localDateParam(LocalDateFormat.ISO, param);
  }

  @Override
  public LocalDate localDateParam(LocalDateFormat format, String param) {
    String value = req.param(param);
    return format.parse(value);
  }

  @Override
  public Double doubleParam(String param) {
    try {
      String value = req.param(param);
      return Double.valueOf(value);
    } catch (NullPointerException e) {
      return null;
    } catch (NumberFormatException e) {
      return null;
    }
  }
  @Override
  public Integer integerParam(String param) {
    try {
      String value = req.param(param);
      return Integer.valueOf(value);
    } catch (NullPointerException e) {
      return null;
    } catch (NumberFormatException e) {
      return null;
    }
  }
  @Override
  public Long longParam(String param) {
    try {
      String value = req.param(param);
      return Long.valueOf(value);
    } catch (NullPointerException e) {
      return null;
    } catch (NumberFormatException e) {
      return null;
    }
  }

  @Override
  public Percentual percentualParam(String key) {
    Percentual percentual = null;

    String text = param(key);
    if (!Strings.isNullOrEmpty(text)) {
      try {
        double doubleValue = parseDouble(text);
        percentual = new Percentual(doubleValue / 100d);
      } catch (ParseException e) {
      }
    }

    return percentual;
  }

  @Override
  public Page getPage() {

    int pageNumber = parsePage();
    int size = getPageSize();
    int start = pageNumber * size;
    return SimplePage.build().startAt(start).withLengthOf(size).get();

  }

  @Override
  public SearchString getSearchString(String param) {
    String string = param(param);
    return new SearchString(string);
  }

  protected Integer getPageParam() {
    return integerParam("page");
  }

  protected int getPageSize() {
    return 100;
  }

  private int parsePage() {
    Integer page = getPageParam();
    return page != null ? page.intValue() : 0;
  }

  protected double parseDouble(String text) throws ParseException {
    Number number = doubleFormat.parse(text);
    return number.doubleValue();
  }

}