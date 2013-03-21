/*
 * BoletoCarteira.java criado em 21/03/2013
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.boleto;

import static br.com.objectos.comuns.boleto.BoletoBanco.BANCO_DO_BRASIL;
import static br.com.objectos.comuns.boleto.BoletoBanco.BRADESCO;
import static br.com.objectos.comuns.boleto.BoletoBanco.ITAU;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public enum BoletoCarteira {

  COBRANCA_SIMPLES_COM_REGISTRO(newMap()
      .put(BANCO_DO_BRASIL, 18)
      .put(BRADESCO, 9)
      .put(ITAU, 112)
      .build());

  private final Map<BoletoBanco, Integer> codigoMap;

  private static Builder<BoletoBanco, Integer> newMap() {
    return ImmutableMap.<BoletoBanco, Integer> builder();
  }

  private BoletoCarteira(Map<BoletoBanco, Integer> codigoMap) {
    this.codigoMap = codigoMap;
  }

  int getCodigo(BoletoBanco banco) {
    int codigo = 1;

    if (codigoMap.containsKey(banco)) {
      codigo = codigoMap.get(banco).intValue();
    }

    return codigo;
  }

}