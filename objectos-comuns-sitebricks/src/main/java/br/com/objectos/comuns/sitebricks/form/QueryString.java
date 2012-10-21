/*
 * QueryString.java criado em 21/11/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks.form;

import static com.google.common.collect.Maps.newLinkedHashMap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.joda.time.LocalDate;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class QueryString implements Supplier<String> {

  private final String url;

  private final LinkedHashMap<String, String> params = newLinkedHashMap();

  public QueryString(String url) {
    this.url = url;
  }

  public QueryString param(String key, Enum<?> value) {
    return param(key, value.name());
  }

  public QueryString param(String key, LocalDate value) {
    return param(key, value.toString());
  }

  public QueryString param(String key, boolean value) {
    return param(key, Boolean.toString(value));
  }
  public QueryString param(String key, double value) {
    return param(key, Double.toString(value));
  }
  public QueryString param(String key, int value) {
    return param(key, Integer.toString(value));
  }
  public QueryString param(String key, long value) {
    return param(key, Long.toString(value));
  }

  public QueryString param(String key, String value) {
    params.put(key, value);
    return this;
  }

  @Override
  public String get() {
    return toString();
  }

  @Override
  public String toString() {
    Set<Entry<String, String>> entries = params.entrySet();
    Iterable<String> parts = Iterables.transform(entries, new ToPart());
    String postUrl = Joiner.on("&").join(parts);

    return String.format("%s?%s", url, postUrl);
  }

  private class ToPart implements Function<Map.Entry<String, String>, String> {
    @Override
    public String apply(Entry<String, String> input) {
      String key = input.getKey();
      String value = input.getValue();
      try {
        String encoded = URLEncoder.encode(value, "UTF-8");
        return String.format("%s=%s", key, encoded);
      } catch (UnsupportedEncodingException e) {
        return String.format("%s=%s", key, value);
      }
    }
  }

}