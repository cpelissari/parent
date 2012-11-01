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
package br.com.objectos.comuns.documentos.base;

import com.sun.star.io.BufferSizeExceededException;
import com.sun.star.io.IOException;
import com.sun.star.io.NotConnectedException;
import com.sun.star.io.XInputStream;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
public class DocumentStream implements XInputStream {

  @Override
  public int available() throws NotConnectedException, IOException {
    return 0;
  }

  @Override
  public void closeInput() throws NotConnectedException, IOException {
  }

  @Override
  public int readBytes(byte[][] arg0, int arg1) throws NotConnectedException,
      BufferSizeExceededException, IOException {
    return 0;
  }

  @Override
  public int readSomeBytes(byte[][] arg0, int arg1) throws NotConnectedException,
      BufferSizeExceededException, IOException {
    return 0;
  }

  @Override
  public void skipBytes(int arg0) throws NotConnectedException, BufferSizeExceededException,
      IOException {
  }

}