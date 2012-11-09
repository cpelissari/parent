/*
 * StringConverter.java criado em 09/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.io.csv;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class StringCsvConverter extends AbstractCsvConverter<String> {

  @Override
  protected String convert(String text) {
    return text;
  }

}