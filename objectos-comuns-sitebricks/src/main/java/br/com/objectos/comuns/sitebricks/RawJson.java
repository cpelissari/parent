/*
 * RawJson.java criado em 27/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import com.google.inject.ImplementedBy;
import com.google.sitebricks.client.Transport;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@ImplementedBy(RawJsonTransport.class)
public abstract class RawJson implements Transport {
  @Override
  public String contentType() {
    return "text/json";
  }
}