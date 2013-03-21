/*
 * NossoNumero.java criado em 21/03/2013
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.boleto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class NossoNumero {

  private static final Pattern pattern = Pattern.compile("[0-9]+");

  private final long numero;

  private final char digito;

  NossoNumero(String numero) {
    Matcher matcher = pattern.matcher(numero);
    Preconditions.checkArgument(matcher.matches());

    int length = numero.length();
    String _numero = numero.substring(0, length - 1);
    this.numero = Long.parseLong(_numero);

    this.digito = numero.charAt(length - 1);
  }

  public int intValue() {
    return (int) numero;
  }
  public long longValue() {
    return numero;
  }

  public char getDigito() {
    return digito;
  }

}