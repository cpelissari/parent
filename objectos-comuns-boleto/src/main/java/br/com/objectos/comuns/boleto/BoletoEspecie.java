/*
 * BoletoEspecie.java criado em 21/03/2013
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.boleto;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public enum BoletoEspecie {

  CH_CHEQUE(1, "CH"),
  DM_DUPLICATA_MERCANTIL(2, "DM"),
  DMI_DUPLICATA_MERCANTIL_PARA_INDICACAO(3, "DMI"),
  DS_DUPLICATA_DE_SERVICO(4, "DS"),
  DSI_DUPLICATA_DE_SERVICO_PARA_INDICACAO(5, "DSI"),
  DR_DUPLICATA_RURAL(6, "DR"),
  LC_LETRA_DE_CAMBIO(7, "LC"),
  NCC_NOTA_DE_CREDITO_COMERCIAL(8, "NCC"),
  NCE_NOTA_DE_CREDITO_A_EXPORTACAO(9, "NCE"),
  NCI_NOTA_DE_CREDITO_INDUSTRIAL(10, "NCI"),
  NCR_NOTA_DE_CREDITO_RURAL(11, "NCR"),
  NP_NOTA_PROMISSORIA(12, "NP"),
  NPR_NOTA_PROMISSORIA_RURAL(13, "NPR"),
  TM_TRIPLICATA_MERCANTIL(14, "TM"),
  TS_TRIPLICATA_DE_SERVICO(15, "TS"),
  NS_NOTA_DE_SEGURO(16, "NS"),
  RC_RECIBO(17, "RC"),
  FAT_FATURA(18, "FAT"),
  ND_NOTA_DE_DEBITO(19, "ND"),
  AP_APOLICE_DE_SEGURO(20, "AP"),
  ME_MENSALIDADE_ESCOLAR(21, "ME"),
  PC_PARCELA_DE_CONSORCIO(22, "PC"),
  NF_NOTA_FISCAL(23, "NF"),
  DD_DOCUMENTO_DE_DIVIDA(24, "DD"),
  CEDULA_DE_PRODUTO_RURAL(25, "Cédula de Produto Rural"),
  WARRANT(26, "Warrant"),
  DIVIDA_ATIVA_DE_ESTADO(27, "Dívida Ativa de Estado"),
  DIVIDA_ATIVA_DE_MUNICIPIO(28, "Dívida Ativa de Município"),
  DIVIDA_ATIVA_DA_UNIAO(29, "Dívida Ativa da União"),
  COTA_CONDOMINIAL(30, "Cota Condominial"),
  OUTROS(99, "Outros");

  private int codigo;

  private String sigla;

  private BoletoEspecie(int codigo, String sigla) {
    this.codigo = codigo;
    this.sigla = sigla;
  }

  public String getSigla() {
    return sigla;
  }

  public int getCodigo() {
    return codigo;
  }

}