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
package br.com.objectos.comuns.web.upload;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.util.Streams;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class UploadedFormFieldApacheCommons implements UploadedFormField {

  private final String name;

  private final String value;

  public UploadedFormFieldApacheCommons(FileItemStream stream) throws IOException {
    InputStream in = stream.openStream();
    name = stream.getFieldName();
    value = Streams.asString(in);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getValue() {
    return value;
  }

}