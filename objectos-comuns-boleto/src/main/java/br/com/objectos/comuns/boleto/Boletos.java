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

import java.util.Calendar;

import org.joda.time.LocalDate;

import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Emissor;
import br.com.caelum.stella.boleto.Sacado;
import br.com.caelum.stella.boleto.transformer.BoletoGenerator;
import br.com.objectos.comuns.base.br.CadastroRFB;
import br.com.objectos.comuns.base.br.Cep;
import br.com.objectos.comuns.base.br.Estado;
import br.com.objectos.comuns.matematica.financeira.ValorFinanceiro;

/**
 * @author ricardo.murad@objectos.com.br (Ricardo Murad)
 */
public class Boletos {

  private final Boleto boleto;
  private final Datas datas;
  private final Sacado sacado;
  private final Emissor emissor;

  Boletos() {
    this.datas = Datas.newDatas();
    this.boleto = Boleto.newBoleto();
    this.sacado = Sacado.newSacado();
    this.emissor = Emissor.newEmissor();
  }

  public static Boletos newBoleto() {
    return new Boletos();
  }

  public byte[] toPdf() {
    join();
    return new BoletoGenerator(boleto)
        .toPDF();
  }

  public byte[] toPng() {
    join();
    return new BoletoGenerator(boleto)
        .toPNG();
  }

  public void toPdf(String filename) {
    join();
    new BoletoGenerator(boleto)
        .toPDF(filename);
  }

  public Boletos contaBancaria(BoletoContaBancaria conta) {
    BoletoBanco banco = conta.getBanco();
    boleto.withBanco(banco.stellaValue());

    BoletoCarteira carteira = conta.getCarteira();
    emissor.withCarteira(carteira.getCodigo(banco));

    String _cc = conta.getNumero();
    NumeroBancario cc = new NumeroBancario(_cc);
    emissor.withContaCorrente(cc.longValue());
    emissor.withDigitoContaCorrente(cc.getDigito());

    String _agencia = conta.getAgencia();
    NumeroBancario agencia = new NumeroBancario(_agencia);
    emissor.withAgencia(agencia.intValue());
    emissor.withDigitoAgencia(agencia.getDigito());

    emissor.withCodigoFornecidoPelaAgencia(conta.getCodFornecidoPelaAgencia());
    emissor.withCodigoOperacao(conta.getCodOperacao());

    return this;
  }

  public Boletos cedente(BoletoCedente cedenteBoleto) {
    emissor.withCedente(cedenteBoleto.getNome());
    emissor.withNumeroConvenio(cedenteBoleto.getNumeroConvenio());

    return this;
  }

  public Boletos sacado(BoletoSacado sacadoBoleto) {
    sacado.withNome(sacadoBoleto.getNome());

    cadastroSacado(sacadoBoleto.getCadastroRFB());

    sacado.withEndereco(sacadoBoleto.getEndereco());
    sacado.withBairro(sacadoBoleto.getBairro());

    Cep cep = sacadoBoleto.getCep();
    int prefixo = cep.getPrefixo();
    int sufixo = cep.getSufixo();
    sacado.withCep(String.format("%05d%03d", prefixo, sufixo));

    sacado.withCidade(sacadoBoleto.getCidade());

    Estado estado = sacadoBoleto.getEstado();
    sacado.withUf(estado != null ? estado.name() : "");

    return this;
  }

  public Boletos titulo(BoletoTitulo titulo) {
    BoletoEspecie especie = titulo.getEspecie();
    boleto.withEspecieDocumento(especie.getSigla());

    ValorFinanceiro valor = titulo.getValor();
    boleto.withValorBoleto(valor.bigDecimalValue());

    Calendar dataDocumento = toCalendar(titulo.getEmissao());
    datas.withDocumento(dataDocumento);

    Calendar dataVencimento = toCalendar(titulo.getVencimento());
    datas.withVencimento(dataVencimento);

    return this;
  }

  public Boletos cobranca(BoletoCobranca cobranca) {
    boleto.withDescricoes(cobranca.getDescricao());
    boleto.withAceite(cobranca.isAceite());
    boleto.withInstrucoes(cobranca.getInstrucao());
    boleto.withLocaisDePagamento(cobranca.getLocalPagamento());
    boleto.withNumeroDoDocumento(cobranca.getNumeroDocumento());

    String _nossoNumero = cobranca.getNossoNumero();
    NossoNumero nossoNumero = new NossoNumero(_nossoNumero);
    emissor.withNossoNumero(nossoNumero.longValue());
    emissor.withDigitoNossoNumero(nossoNumero.getDigito());

    Calendar dataProcessamento = toCalendar(cobranca.getDataProcessamento());
    datas.withProcessamento(dataProcessamento);

    return this;
  }

  private Boletos cadastroSacado(CadastroRFB cadastro) {
    long longValue = cadastro.longValue();
    sacado.withCpf(Long.toString(longValue));
    return this;
  }

  private Calendar toCalendar(LocalDate data) {
    Calendar calendar = Calendar.getInstance();

    if (data != null) {
      calendar.setTime(data.toDate());
    }

    return calendar;
  }

  private void join() {
    boleto.withDatas(datas);
    boleto.withEmissor(emissor);
    boleto.withSacado(sacado);
  }

}