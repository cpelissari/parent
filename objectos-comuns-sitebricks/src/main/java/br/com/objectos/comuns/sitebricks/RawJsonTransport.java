/*
 * RawJsonTransport.java criado em 27/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import br.com.objectos.comuns.sitebricks.SearchQueryReplyGen.Json;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class RawJsonTransport extends RawJson {

  @Override
  public <T> T in(InputStream in, Class<T> type) throws IOException {
    return type.cast(IOUtils.toString(in));
  }

  @Override
  public <T> void out(OutputStream out, Class<T> type, T data) {
    try {
      String jsonData;
      if (data instanceof SearchQueryReplyGen.Json) {
        SearchQueryReplyGen.Json json = (Json) data;
        jsonData = String.format("{\"total\":%d,\"data\":%s}", json.getTotal(), json.getData());
      } else {
        jsonData = data.toString();
      }
      IOUtils.write(jsonData, out);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}