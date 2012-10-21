/*
 * Page.java criado em 17/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public interface Page {

  String getTitle();

  String getBreadcrumbs();

  String getUrl();

}