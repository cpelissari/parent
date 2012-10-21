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
package br.com.objectos.comuns.io;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ColumnConversionException extends ComunsIOException {

  private static final long serialVersionUID = 1L;

  private final Object value;

  private final Class<?> type;

  public ColumnConversionException(Object value, Class<?> type) {
    this.value = value;
    this.type = type;
  }

  public ColumnConversionException(Exception e, Object value, Class<?> type) {
    super(e);
    this.value = value;
    this.type = type;
  }

  @Override
  public String getMessage() {
    return String.format("Could not convert %s to %s", value, type);
  }

}