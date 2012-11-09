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

import br.com.objectos.comuns.base.Builder;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class FakePotentialFileBuilder {

  public InvalidBuilder invalid() {
    return new InvalidBuilder();
  }

  public ValidBuilder valid() {
    return new ValidBuilder();
  }

  public static class InvalidBuilder implements Builder<PotentialFile> {

    private Exception cause;

    @Override
    public PotentialFile build() {
      return new PotentialFileFailed(cause);
    }

    public InvalidBuilder cause(Exception cause) {
      this.cause = cause;
      return this;
    }

  }

  public static class ValidBuilder implements Builder<PotentialFile> {

    private File file;

    private String contentType;

    @Override
    public PotentialFile build() {
      UploadedFile uploadedFile = new FakeUploadedFile(file, contentType);
      return new PotentialFileSucceeded(uploadedFile);
    }

    public ValidBuilder file(File file) {
      this.file = file;
      return this;
    }

    public ValidBuilder contentType(String contentType) {
      this.contentType = contentType;
      return this;
    }

  }

}