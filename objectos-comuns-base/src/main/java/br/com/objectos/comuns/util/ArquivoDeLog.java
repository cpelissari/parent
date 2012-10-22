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
package br.com.objectos.comuns.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.objectos.comuns.io.TailFileReader;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ArquivoDeLog {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private final File arquivo;

  private PrintWriter log;

  public ArquivoDeLog(File arquivo) {
    this.arquivo = arquivo;
  }

  public void bindLoggerTo(Logger logger) {
    this.logger = logger;
  }

  public void open() {
    try {

      FileWriter fileWriter = new FileWriter(arquivo, true);
      log = new PrintWriter(fileWriter, false);

    } catch (IOException e) {

      throw new ExcecaoDeArquivoDeLog(e);

    }
  }

  public void flush() {
    log.flush();
  }

  public void close() {
    flush();
    log.close();
  }

  public File getArquivo() {
    return arquivo;
  }

  public void debug(String conteudo, Object... opcoes) {
    String msg = msg(conteudo, opcoes);
    logger.debug(msg);
  }

  public void info(String conteudo, Object... opcoes) {
    String msg = msg(conteudo, opcoes);
    logger.info(msg);
    write("I", msg);
  }

  public void error(String conteudo, Object... opcoes) {
    String msg = msg(conteudo, opcoes);
    logger.error(msg);
    write("E", msg);
  }

  public void error(String conteudo, Throwable e, Object... opcoes) {
    String msg = msg(conteudo, opcoes);
    logger.error(msg, e);
    write("E", msg);
    e.printStackTrace(log);
  }

  public TailFileReader toToFileReader() {
    return new TailFileReader(arquivo);
  }

  private void write(String nivel, String msg) {
    Preconditions.checkState(log != null,
        "Você precisa chamar o método open() antes de escrever neste log.");

    String header = header(nivel);
    log.println(header + " " + msg);
  }

  private String header(String nivel) {
    String formato = "[%1$s] %2$tY-%2$tm-%2$td %2$tH:%2$tM:%2$tS";
    String header = String.format(formato, nivel, new Date());
    return header;
  }

  private String msg(String conteudo, Object... opcoes) {
    return String.format(conteudo, opcoes);
  }

}