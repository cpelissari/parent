/*
 * HtmlTransport.java criado em 25/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class HtmlTransport extends Html {

  @Override
  public <T> T in(InputStream in, Class<T> type) throws IOException {
    return type.cast(IOUtils.toString(in));
  }

  @Override
  public <T> void out(OutputStream out, Class<T> type, T data) {
    try {
      IOUtils.write(data.toString(), out);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}