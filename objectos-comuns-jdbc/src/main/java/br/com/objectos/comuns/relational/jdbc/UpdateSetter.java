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

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public interface UpdateSetter {

  UpdateSetter set(String colName, BigDecimal value);

  UpdateSetter set(String colName, Boolean value);

  UpdateSetter set(String colName, java.util.Date value);

  UpdateSetter set(String colName, DateTime value);

  UpdateSetter set(String colName, Double value);

  UpdateSetter set(String colName, Float value);

  UpdateSetter set(String colName, Integer value);

  UpdateSetter set(String colName, LocalDate value);

  UpdateSetter set(String colName, Long value);

  UpdateSetter set(String colName, String value);

}