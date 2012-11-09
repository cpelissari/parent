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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public final class Bradesco implements Modelo {

  private static enum Singleton {

    INSTANCE;

    private final Bradesco instance;

    private Singleton() {
      BradescoHeaderSpec headerSpec = new BradescoHeaderSpec();
      BradescoLoteSpec loteSpec = new BradescoLoteSpec();
      this.instance = new Bradesco(headerSpec, loteSpec);
    }

    public Bradesco get() {
      return instance;
    }

  }

  public static final Bradesco banco = Singleton.INSTANCE.get();

  public static final OcorrenciaParser ocorrenciaParser = new BradescoOcorrenciaParser();

  private final BradescoHeaderSpec headerSpec;

  private final BradescoLoteSpec loteSpec;

  private Bradesco(BradescoHeaderSpec headerSpec, BradescoLoteSpec loteSpec) {
    this.headerSpec = headerSpec;
    this.loteSpec = loteSpec;
  }

  public static BradescoHeader header() {
    return banco.headerSpec;
  }
  public static BradescoLote lote() {
    return banco.loteSpec;
  }

  @Override
  public Spec getHeaderSpec() {
    return headerSpec;
  }

  @Override
  public Spec getLoteSpec() {
    return loteSpec;
  }

}