/*
 * Quality.java criado em 04/10/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public enum Method {

  AUTOMATIC,

  SPEED,

  BALANCED,

  QUALITY,

  ULTRA_QUALITY;

  org.imgscalr.Scalr.Method toScalr() {
    return org.imgscalr.Scalr.Method.valueOf(name());
  }

}