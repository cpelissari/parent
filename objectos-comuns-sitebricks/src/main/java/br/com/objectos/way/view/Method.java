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
package br.com.objectos.way.view;

import java.lang.annotation.Annotation;

import com.google.sitebricks.headless.Request;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
enum Method {

  GET,
  POST,
  PUT,
  DELETE;

  public static Method parse(Request request) {
    String method = request.method();
    return Method.valueOf(method.toUpperCase());
  }

  public static Method parse(Class<? extends Annotation> annotation) {
    String method = annotation.getSimpleName();
    return Method.valueOf(method.toUpperCase());
  }

}