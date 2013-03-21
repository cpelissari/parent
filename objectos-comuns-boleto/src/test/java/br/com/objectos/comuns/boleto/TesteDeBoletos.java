/*
 * Copyright 2012 Objectos, Fábrica de Software LTDA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package br.com.objectos.comuns.boleto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import org.joda.time.LocalDate;
import org.testng.annotations.Test;

import br.com.objectos.comuns.base.br.CadastroRFB;
import br.com.objectos.comuns.base.br.Cep;
import br.com.objectos.comuns.base.br.Cpf;
import br.com.objectos.comuns.base.br.Estado;
import br.com.objectos.comuns.matematica.financeira.ValorFinanceiro;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
@Test
public class TesteDeBoletos {

  public void gerar_boleto_em_formato_pdf() throws Exception {
    String contra = "src/test/resources/contra.pdf";
    File saida = File.createTempFile("objectos-comuns-boleto-", ".pdf");
    String resultado = saida.getAbsolutePath();

    ContaBancaria conta = new ContaBancaria();
    Sacado sacado = new Sacado();
    Cedente cedente = new Cedente();
    Titulo titulo = new Titulo();
    Cobranca cobranca = new Cobranca();

    Boletos.newBoleto()
        .contaBancaria(conta)
        .cedente(cedente)
        .sacado(sacado)
        .titulo(titulo)
        .cobranca(cobranca)

        .toPdf(resultado);

    String c1 = PdfToText.fromFile(contra);
    String c2 = PdfToText.fromFile(resultado);

    assertThat(c1, equalTo(c2));
  }

  private class ContaBancaria implements BoletoContaBancaria {

    @Override
    public BoletoBanco getBanco() {
      return BoletoBanco.BANCO_DO_BRASIL;
    }

    @Override
    public String getAgencia() {
      return "238-3";
    }

    @Override
    public String getNumero() {
      return "127076-2";
    }

    @Override
    public int getCodFornecidoPelaAgencia() {
      return 987;
    }

    @Override
    public int getCodOperacao() {
      return 9;
    }

    @Override
    public BoletoCarteira getCarteira() {
      return BoletoCarteira.COBRANCA_SIMPLES_COM_REGISTRO;
    }

  }

  private class Cedente implements BoletoCedente {

    @Override
    public String getNome() {
      return "Paulo da Silva";
    }

    @Override
    public long getNumeroConvenio() {
      return 456;
    }

  }

  private class Sacado implements BoletoSacado {

    @Override
    public String getNome() {
      return "Marina Santos";
    }

    @Override
    public CadastroRFB getCadastroRFB() {
      return Cpf.parseCPF("305.673,628/50");
    }

    @Override
    public String getEndereco() {
      return "Avenida Paulista, 1000";
    }

    @Override
    public String getBairro() {
      return "Bela Vista";
    }

    @Override
    public Cep getCep() {
      return Cep.valueOf("01310-100");
    }

    @Override
    public String getCidade() {
      return "São Paulo";
    }

    @Override
    public Estado getEstado() {
      return Estado.SP;
    }

  }

  private class Titulo implements BoletoTitulo {

    @Override
    public BoletoEspecie getEspecie() {
      return BoletoEspecie.DM_DUPLICATA_MERCANTIL;
    }

    @Override
    public ValorFinanceiro getValor() {
      return new ValorFinanceiroImpl(177.4);
    }

    @Override
    public LocalDate getEmissao() {
      return new LocalDate(2012, 7, 22);
    }

    @Override
    public LocalDate getVencimento() {
      return new LocalDate(2014, 9, 28);
    }

  }

  private class Cobranca implements BoletoCobranca {

    @Override
    public String getDescricao() {
      return "01 Produto XY";
    }

    @Override
    public boolean isAceite() {
      return false;
    }

    @Override
    public String getInstrucao() {
      return "Não receber após a data de vencimento.";
    }

    @Override
    public String getLocalPagamento() {
      return "Pagável em qualquer agência até a data de vencimento";
    }

    @Override
    public String getNumeroDocumento() {
      return "124365";
    }

    @Override
    public String getNossoNumero() {
      return "9666910";
    }

    @Override
    public LocalDate getDataProcessamento() {
      return new LocalDate(2013, 8, 25);
    }

  }

}