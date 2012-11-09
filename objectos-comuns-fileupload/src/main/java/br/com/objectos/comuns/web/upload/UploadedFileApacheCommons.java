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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class UploadedFileApacheCommons implements UploadedFile {

  private final FileItemStream stream;

  public UploadedFileApacheCommons(FileItemStream stream) {
    this.stream = stream;
  }

  @Override
  public InputStream openStream() throws UploadRequestException {
    try {
      return stream.openStream();
    } catch (IOException e) {
      throw new UploadRequestException(e);
    }
  }

  @Override
  public String getContentType() {
    return stream.getContentType();
  }

  @Override
  public String getName() {
    return stream.getName();
  }

}