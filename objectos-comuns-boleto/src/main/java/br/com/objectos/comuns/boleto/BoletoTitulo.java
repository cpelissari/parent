/*
 * TituloBoleto.java criado em 19/03/2013
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.boleto;

import org.joda.time.LocalDate;

import br.com.objectos.comuns.matematica.financeira.ValorFinanceiro;

/**
 * @author edenir.anschau@objectos.com.br (Edenir Norberto Anschau)
 */
public interface BoletoTitulo {

  BoletoEspecie getEspecie();

  ValorFinanceiro getValor();

  LocalDate getEmissao();
  LocalDate getVencimento();

}