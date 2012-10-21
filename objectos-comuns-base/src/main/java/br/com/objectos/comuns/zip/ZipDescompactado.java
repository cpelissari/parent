/*
 * Copyright 2011 Objectos, Fábrica de Software LTDA.
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
package br.com.objectos.comuns.zip;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newArrayListWithCapacity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ZipDescompactado {

  private final List<File> arquivos;

  private final List<ExcecaoDeZip> excecoes;

  public ZipDescompactado() {
    arquivos = newArrayList();
    excecoes = newArrayList();
  }

  public List<File> getArquivos() {
    return arquivos;
  }

  public List<ExcecaoDeZip> getExcecoes() {
    return excecoes;
  }

  public List<InputStream> toInputStream() throws FileNotFoundException {
    List<InputStream> resultado = newArrayListWithCapacity(arquivos.size());

    for (File file : arquivos) {
      resultado.add(new FileInputStream(file));
    }

    return resultado;
  }

  void adicionar(File arquivo) {
    arquivos.add(arquivo);
  }

  void adicionar(Exception excecao, String nome) {
    excecoes.add(new ExcecaoDeZip(excecao, nome));
  }

}