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

import java.util.Iterator;

import br.com.objectos.comuns.io.FixedLine;
import br.com.objectos.comuns.io.ParsedFixedLines;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class HeaderParser {

  private final ParsedFixedLines lines;

  public HeaderParser(ParsedFixedLines lines) {
    this.lines = lines;
  }

  public Header get() {
    Iterator<FixedLine> iterator = lines.iterator();
    PeekingIterator<FixedLine> peeking = Iterators.peekingIterator(iterator);

    FixedLine line = peeking.peek();

    Integer tipo = line.column(0, 1).get(Integer.class);
    if (tipo.intValue() != 0 || line.getText().length() != 400) {
      throw new ExcecaoCnab(
          line, "Primeira linha não corresponde a um header CNAB400. " +
              "Talvez você tenha enviado o arquivo incorreto?");
    }

    Integer codigo = line.column(76, 79).get(Integer.class);
    Banco banco = Banco.valueOf(codigo.intValue());
    Modelo modelo = banco.getModelo();

    return new HeaderPadrao(banco, modelo, line);
  }

}