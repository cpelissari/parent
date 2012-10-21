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
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.common.collect.AbstractIterator;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class UploadRequestsGuice implements UploadRequests {

  private final Provider<HttpServletRequest> requestProvider;

  @Inject
  public UploadRequestsGuice(Provider<HttpServletRequest> requestProvider) {
    this.requestProvider = requestProvider;
  }

  @Override
  public UploadRequest get() {
    return new UploadRequest() {

      @Override
      public Iterable<PotentialFile> getPotentialFiles() {
        return new TheIterable();
      }

    };
  }

  private class TheIterable implements Iterable<PotentialFile> {
    @Override
    public Iterator<PotentialFile> iterator() {
      return new TheIterator();
    }
  }

  private class TheIterator extends AbstractIterator<PotentialFile> {

    private final FileItemIterator delegate;

    private final PotentialFile firstAndOnly;

    private boolean stopNow = false;

    public TheIterator() {
      HttpServletRequest request = requestProvider.get();
      ServletFileUpload upload = new ServletFileUpload();

      FileItemIterator delegate = null;
      PotentialFile firstAndOnly = null;

      try {
        delegate = upload.getItemIterator(request);

      } catch (FileUploadException e) {
        firstAndOnly = new PotentialFileFailed(e);

      } catch (IOException e) {
        firstAndOnly = new PotentialFileFailed(e);

      }

      this.delegate = delegate;
      this.firstAndOnly = firstAndOnly;
    }

    @Override
    protected PotentialFile computeNext() {
      if (stopNow) {
        return endOfData();
      }

      if (firstAndOnly != null) {
        stopNow = true;
        return firstAndOnly;
      }

      PotentialFile next = null;

      try {
        next = getNext();

      } catch (FileUploadException e) {
        next = new PotentialFileFailed(e);

      } catch (IOException e) {
        next = new PotentialFileFailed(e);

      }

      return next != null ? next : endOfData();
    }

    private PotentialFile getNext() throws FileUploadException, IOException {
      PotentialFile next = null;

      while (delegate.hasNext()) {
        FileItemStream stream = delegate.next();

        if (!stream.isFormField()) {
          UploadedFile file = new UploadedFileApacheCommons(stream);
          next = new PotentialFileSucceeded(file);
          break;
        }
      }

      return next;
    }
  }

}