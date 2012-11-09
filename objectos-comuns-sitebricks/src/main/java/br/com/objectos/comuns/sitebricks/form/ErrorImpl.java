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
package br.com.objectos.comuns.sitebricks.form;

import javax.validation.ConstraintViolation;

import com.google.common.base.Throwables;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ErrorImpl implements FormResponse.Error {

  private final String name;

  private final String message;

  private final String messageBody;

  public ErrorImpl(ConstraintViolation<?> violation) {
    this.name = violation.getPropertyPath().toString();
    this.message = violation.getMessage();
    this.messageBody = "";
  }

  public ErrorImpl(String name, String message) {
    this.name = name;
    this.message = message;
    this.messageBody = "";
  }

  public ErrorImpl(String name, Throwable t) {
    this.name = name;
    this.message = t.getMessage();
    this.messageBody = Throwables.getStackTraceAsString(t);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public String getMessageBody() {
    return messageBody;
  }

}