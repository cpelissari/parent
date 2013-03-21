/*
 * BoletoCobranca.java criado em 21/03/2013
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.boleto;

import org.joda.time.LocalDate;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public interface BoletoCobranca {

  String getDescricao();

  LocalDate getDataProcessamento();

  String getNossoNumero();

  boolean isAceite();

  String getInstrucao();
  String getLocalPagamento();
  String getNumeroDocumento();

}