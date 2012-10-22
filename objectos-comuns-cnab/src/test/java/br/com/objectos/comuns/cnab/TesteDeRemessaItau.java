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
package br.com.objectos.comuns.cnab;

import static com.google.common.collect.Lists.newArrayList;
import static org.testng.Assert.fail;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

import org.joda.time.LocalDate;
import org.testng.annotations.Test;

import br.com.objectos.comuns.cnab.obj.Agencia;
import br.com.objectos.comuns.cnab.obj.AgenciasFalso;
import br.com.objectos.comuns.cnab.obj.Carteira;
import br.com.objectos.comuns.cnab.obj.CedentesFalso;
import br.com.objectos.comuns.cnab.obj.Cobranca;
import br.com.objectos.comuns.cnab.obj.CobrancaOpcoes;
import br.com.objectos.comuns.cnab.obj.Comando;
import br.com.objectos.comuns.cnab.obj.ConstrutorDeCobrancaFalso;
import br.com.objectos.comuns.cnab.obj.ConstrutorDeTituloFalso;
import br.com.objectos.comuns.cnab.obj.Conta;
import br.com.objectos.comuns.cnab.obj.ContasFalso;
import br.com.objectos.comuns.cnab.obj.Empresa;
import br.com.objectos.comuns.cnab.obj.EmpresasFalso;
import br.com.objectos.comuns.cnab.obj.EspecieDeTitulo;
import br.com.objectos.comuns.cnab.obj.Instrucao;
import br.com.objectos.comuns.cnab.obj.SacadosFalso;
import br.com.objectos.comuns.cnab.obj.Titulo;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;

/**
 * @author marcos.piazzolla@objectos.com.br (Marcos Piazzolla)
 */
@Test
public class TesteDeRemessaItau {

  Banco banco = Banco.ITAU;

  Empresa empresa = EmpresasFalso.OBJECTOS;
  int sequenciaArquivo = 720;
  LocalDate dataArquivo = new LocalDate(2012, 10, 23);

  Carteira carteira = Carteira.COBRANCA_SIMPLES_COM_REGISTRO;
  Agencia agencia = AgenciasFalso.AGENCIA_A;
  Conta conta = ContasFalso.CONTA_1;

  Instrucao instrucao1 = ItauInstrucao.get(9).with(5);
  Instrucao instrucao2 = ItauInstrucao.get(52).with();

  public void integracao() throws IOException {
    Titulo t0 = novoTitulo()
        .usoDaEmpresa("TIT_00")
        .especie(EspecieDeTitulo.DUPLICATA)
        .numero("TIT0/BBDC")
        .cedente(CedentesFalso.ARMAZEM)
        .sacado(SacadosFalso.MACHADO_ASSIS)
        .prazo(2)
        .emissao(new LocalDate(2012, 10, 9))
        .vencimento(new LocalDate(2012, 11, 12))
        .valor(948.14)
        .novaInstancia();
    CobrancaOpcoes opt0 = CobrancaOpcoes.padrao()
        .moraDia(2.53)
        .instrucao1(instrucao1)
        .instrucao2(instrucao2);
    Cobranca cob0 = novaCobranca()
        .comando(Comando.REMESSA)
        .titulo(t0)
        .opcoes(opt0)
        .novaInstancia();

    Titulo t1 = novoTitulo()
        .usoDaEmpresa("TIT_01")
        .especie(EspecieDeTitulo.DUPLICATA)
        .numero("TIT1/BBDC")
        .cedente(CedentesFalso.LOJA)
        .sacado(SacadosFalso.JOSE_ALENCAR)
        .prazo(2)
        .emissao(new LocalDate(2012, 10, 9))
        .vencimento(new LocalDate(2012, 11, 6))
        .valor(4715.76)
        .novaInstancia();
    CobrancaOpcoes opt1 = CobrancaOpcoes.padrao()
        .moraDia(12.58)
        .instrucao1(instrucao1)
        .instrucao2(instrucao2);
    Cobranca cob1 = novaCobranca()
        .comando(Comando.REMESSA)
        .titulo(t1)
        .opcoes(opt1)
        .novaInstancia();

    List<Cobranca> cobrancas = ImmutableList.of(cob0, cob1);

    String res = Cnab.remessaParaBanco(banco)

        .empresa(empresa)
        .agencia(agencia)
        .conta(conta)
        .sequenciaArquivo(sequenciaArquivo)
        .dataArquivo(dataArquivo)

        .cobrancas(cobrancas)

        .toString();

    StringReader reader = new StringReader(res);
    List<String> lines = CharStreams.readLines(reader);

    assertRemessa(lines);
  }
  private ConstrutorDeCobrancaFalso novaCobranca() {
    return new ConstrutorDeCobrancaFalso()
        .carteira(carteira)
        .agencia(agencia)
        .conta(conta);
  }

  private ConstrutorDeTituloFalso novoTitulo() {
    return new ConstrutorDeTituloFalso();
  }

  private void assertRemessa(List<String> lines) throws IOException {
    URL url = Resources.getResource(getClass(), "/remessa-itau.txt");
    List<String> prova = Resources.readLines(url, Charsets.UTF_8);
    List<String> invalids = newArrayList();

    for (int i = 0; i < prova.size(); i++) {
      String exp = prova.get(i);
      String res = null;
      if (i < lines.size()) {
        res = lines.get(i);
      }

      Line line = new Line(i, exp, res);
      if (!line.valid()) {
        invalids.add(line.toString());
      }
    }

    if (!invalids.isEmpty()) {
      String output = Joiner.on("\n").join(invalids);
      System.out.println(output);
      fail();
    }
  }

}