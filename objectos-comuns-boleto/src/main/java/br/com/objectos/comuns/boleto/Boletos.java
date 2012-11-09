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
import br.com.objectos.comuns.base.br.Cnpj;
import br.com.objectos.comuns.base.br.Cpf;
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

  public Boletos nomeCedente(String nome) {
    emissor.withCedente(nome);
    return this;
  }

  public Boletos agencia(int agencia) {
    emissor.withAgencia(agencia);
    return this;
  }

  public Boletos digitoAgencia(char digito) {
    emissor.withDigitoAgencia(digito);
    return this;
  }

  public Boletos contaCorrente(long conta) {
    emissor.withContaCorrente(conta);
    return this;
  }

  public Boletos digitoContaCorrente(char digito) {
    emissor.withDigitoContaCorrente(digito);
    return this;
  }

  public Boletos codigoAgencia(int codigo) {
    emissor.withCodigoFornecidoPelaAgencia(codigo);
    return this;
  }

  public Boletos codigoOperacao(int codigo) {
    emissor.withCodigoOperacao(codigo);
    return this;
  }

  public Boletos carteira(int carteira) {
    emissor.withCarteira(carteira);
    return this;
  }

  public Boletos nossoNumero(long numero) {
    emissor.withNossoNumero(numero);
    return this;
  }
  public Boletos digitoNossoNumero(char digito) {
    emissor.withDigitoNossoNumero(digito);
    return this;
  }

  public Boletos numeroConvenio(long convenio) {
    emissor.withNumeroConvenio(convenio);
    return this;
  }

  public Boletos nomeSacado(String nome) {
    sacado.withNome(nome);
    return this;
  }

  public Boletos cadastroSacado(CadastroRFB cadastro) {
    long longValue = cadastro.longValue();
    sacado.withCpf(Long.toString(longValue));
    return this;
  }
  public Boletos cnpjSacado(Cnpj cnpj) {
    return cadastroSacado(cnpj);
  }
  public Boletos cpfSacado(Cpf cpf) {
    return cadastroSacado(cpf);
  }

  public Boletos enderecoSacado(String endereco) {
    sacado.withEndereco(endereco);
    return this;
  }

  public Boletos bairroSacado(String bairro) {
    sacado.withBairro(bairro);
    return this;
  }

  public Boletos cepSacado(Cep cep) {
    int prefixo = cep.getPrefixo();
    int sufixo = cep.getSufixo();
    sacado.withCep(String.format("%05d%03d", prefixo, sufixo));
    return this;
  }
  public Boletos cidadeSacado(String cidade) {
    sacado.withCidade(cidade);
    return this;
  }

  public Boletos estadoSacado(String estado) {
    sacado.withUf(estado);
    return this;
  }

  public Boletos dataVencimento(LocalDate data) {
    datas.withVencimento(toCalendar(data));
    return this;
  }

  public Boletos dataProcessamento(LocalDate data) {
    datas.withProcessamento(toCalendar(data));
    return this;
  }

  public Boletos dataDocumento(LocalDate data) {
    datas.withDocumento(toCalendar(data));
    return this;
  }

  private Calendar toCalendar(LocalDate data) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(data.toDate());
    return calendar;
  }

  public Boletos valorDoBoleto(double valor) {
    boleto.withValorBoleto(valor);
    return this;
  }

  public Boletos valorDoBoleto(ValorFinanceiro valor) {
    double val = valor.doubleValue();
    boleto.withValorBoleto(val);
    return this;
  }

  public Boletos descricao(String descricao) {
    boleto.withDescricoes(descricao);
    return this;
  }

  public Boletos aceite(boolean aceite) {
    boleto.withAceite(aceite);
    return this;
  }

  public Boletos instrucoes(String instrucoes) {
    boleto.withInstrucoes(instrucoes);
    return this;
  }

  public Boletos locaisDepagamento(String locaisDePagamento) {
    boleto.withLocaisDePagamento(locaisDePagamento);
    return this;
  }

  public Boletos numeroDocumento(String numero) {
    boleto.withNumeroDoDocumento(numero);
    return this;
  }

  public Boletos especieDocumento(String especie) {
    boleto.withEspecieDocumento(especie);
    return this;
  }

  public Boletos paraBanco(BoletoBanco enumBanco) {
    boleto.withBanco(enumBanco.getbanco());
    return this;
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

  public static Boletos newBoleto() {
    return new Boletos();
  }

  private void join() {
    boleto.withDatas(datas);
    boleto.withEmissor(emissor);
    boleto.withSacado(sacado);
  }

}