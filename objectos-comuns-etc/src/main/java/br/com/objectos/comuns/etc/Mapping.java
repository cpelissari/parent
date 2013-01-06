/*
 * Mapping.java criado em 07/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc;

import java.util.List;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public interface Mapping {

  Mapping getMapping(String key);

  List<Mapping> getSequence(String key);

  String getString(String key);

  boolean getBoolean(String key);

  int getInt(String key);
  long getLong(String key);

  float getFloat(String key);
  double getDouble(String key);

}