/*
 * Copyright 2012 Objectos, Fábrica de Software LTDA.
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
package br.com.objectos.comuns.sitebricks.relational;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import br.com.objectos.comuns.sitebricks.LocalDateFormat;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class SearchString {

  private final Param param;

  private final Map<String, Param> paramMap;

  public SearchString(String string) {
    String val = Strings.nullToEmpty(string);
    val = val.trim();

    List<String> mainsParts = newArrayList();
    Map<String, Param> paramMap = newHashMap();

    Iterable<String> parts = Splitter.onPattern("\\s+").split(val);
    for (String part : parts) {
      int index = part.indexOf(':');

      if (index == -1) {
        mainsParts.add(part);
      } else {

        String key = part.substring(0, index);
        String value = part.substring(index + 1);

        paramMap.put(key, new Param(value));

      }
    }

    String main = Joiner.on(" ").join(mainsParts);
    this.param = new Param(main);
    this.paramMap = ImmutableMap.copyOf(paramMap);
  }

  public static ToStringHelper toStringHelper() {
    return new ToStringHelper();
  }

  public Param param() {
    return param;
  }

  public Param param(String key) {
    Param param = new EmptyParam();

    if (paramMap.containsKey(key)) {
      param = paramMap.get(key);
    }

    return param;
  }

  public boolean isEmpty() {
    boolean empty = true;

    empty = empty && param.isEmpty();

    if (empty) {
      for (Param param : paramMap.values()) {
        empty = empty && param.isEmpty();
      }
    }

    return empty;
  }

  public static class Param {

    private final String value;

    public Param(String value) {
      this.value = Preconditions.checkNotNull(value);
    }

    public boolean isEmpty() {
      return Strings.isNullOrEmpty(value);
    }

    public LocalDate toLocalDate(LocalDateFormat format) {
      return format.parse(value);
    }

    public Double toDouble() {
      try {
        return Double.valueOf(value);
      } catch (NumberFormatException e) {
        return null;
      }
    }
    public Integer toInteger() {
      try {
        return Integer.valueOf(value);
      } catch (NumberFormatException e) {
        return null;
      }
    }

    @Override
    public String toString() {
      return value;
    }

  }

  private static class EmptyParam extends Param {

    public EmptyParam() {
      super("");
    }

  }

  public static class ToStringHelper {

    private final List<String> parts = newArrayList();

    private ToStringHelper() {
    }

    public ToStringHelper add(String key, String value) {
      Preconditions.checkNotNull(key);
      String val = Strings.nullToEmpty(value);
      this.parts.add(key + ":" + val);
      return this;
    }

    public ToStringHelper addValue(String value) {
      String val = Strings.nullToEmpty(value);
      this.parts.add(val);
      return this;
    }

    @Override
    public String toString() {
      return Joiner.on(" ").join(parts);
    }

  }

}