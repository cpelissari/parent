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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class PotentialFileFailed implements PotentialFile {

  private final Exception e;

  public PotentialFileFailed(Exception e) {
    this.e = e;
  }

  @Override
  public UploadedFile get() throws UploadRequestException {
    throw new UploadRequestException(e);
  }

  @Override
  public UploadedFile saveAndGet() throws UploadRequestException {
    throw new UploadRequestException(e);
  }

}