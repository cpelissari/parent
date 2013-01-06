/*
 * Parser.java criado em 07/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public interface ConfigLoader<T> {

  T load(Mapping map);

}