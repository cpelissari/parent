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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.google.common.io.Files;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class FakeUploadedFile implements UploadedFile {

  private final File file;

  private final String contentType;

  public FakeUploadedFile(File file, String contentType) {
    this.file = file;
    this.contentType = contentType;
  }

  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public String getName() {
    return file.getName();
  }

  @Override
  public InputStream openStream() throws UploadRequestException {
    try {
      return Files.newInputStreamSupplier(file).getInput();
    } catch (IOException e) {
      throw new UploadRequestException(e);
    }
  }

}