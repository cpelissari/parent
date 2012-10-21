/*
 * ToJsonMap.java criado em 09/10/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.sitebricks.client.WebResponse;
import com.google.sitebricks.client.transport.Json;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ToJsonMap {

  private final WebResponse response;

  public ToJsonMap(WebResponse response) {
    this.response = response;
  }

  public List<Map<?, ?>> get() {
    Map<?, ?>[] maps = response.to(Map[].class).using(Json.class);
    return ImmutableList.copyOf(maps);
  }

}