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
package br.com.objectos.comuns.cnab.bradesco;

import static com.google.common.collect.Lists.transform;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.objectos.comuns.cnab.Cnab;
import br.com.objectos.comuns.cnab.CnabsFalso;
import br.com.objectos.comuns.cnab.Lote;
import br.com.objectos.comuns.cnab.Ocorrencia;
import br.com.objectos.comuns.cnab.OcorrenciaCodigo;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TesteDeOcorrenciaBradesco {

  private List<Ocorrencia> ocorrencias;

  @BeforeClass
  public void prepararOcorrencias() {
    File file = CnabsFalso.RETORNO_237_01.getFile();
    List<Lote> lotes = Cnab.retornoDe(file).getLotes();
    ocorrencias = transform(lotes, new ToOcorrencia());
    assertThat(ocorrencias.size(), equalTo(31));
  }

  public void codigos() {
    List<String> prova = ImmutableList.<String> builder()
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("06:Liquidação normal")
        .add("10:Baixado conforme instruções da Agência")
        .add("10:Baixado conforme instruções da Agência")
        .add("14:Vencimento Alterado")
        .add("14:Vencimento Alterado")
        .add("33:Confirmação Pedido Alteração Outros Dados")
        .add("33:Confirmação Pedido Alteração Outros Dados")
        .build();

    List<String> res = transform(ocorrencias, new ToCodigo());

    assertThat(res, equalTo(prova));
  }

  private static class ToOcorrencia implements Function<Lote, Ocorrencia> {
    @Override
    public Ocorrencia apply(Lote input) {
      return input.get(Cnab.lote().ocorrencia());
    }
  }

  private static class ToCodigo implements Function<Ocorrencia, String> {
    @Override
    public String apply(Ocorrencia input) {
      OcorrenciaCodigo codigo = input.getCodigo();
      String descricao = input.getDescricao();
      return String.format("%s:%s", codigo, descricao);
    }
  }

}