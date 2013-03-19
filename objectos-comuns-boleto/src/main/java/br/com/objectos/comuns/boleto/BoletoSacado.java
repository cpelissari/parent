/*
 * SacadoBoleto.java criado em 19/03/2013
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.boleto;

import br.com.objectos.comuns.base.br.CadastroRFB;
import br.com.objectos.comuns.base.br.Cep;
import br.com.objectos.comuns.base.br.Estado;

/**
 * @author edenir.anschau@objectos.com.br (Edenir Norberto Anschau)
 */
public interface BoletoSacado {

  String getNome();

  CadastroRFB getCadastroRFB();

  String getEndereco();

  String getBairro();

  Cep getCep();

  String getCidade();

  Estado getEstado();

}