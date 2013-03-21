/*
 * ContaBancaria.java criado em 19/03/2013
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.boleto;

/**
 * @author edenir.anschau@objectos.com.br (Edenir Norberto Anschau)
 */
public interface BoletoContaBancaria {

  BoletoBanco getBanco();
  BoletoCarteira getCarteira();

  String getAgencia();
  String getNumero();

  int getCodFornecidoPelaAgencia();
  int getCodOperacao();

}