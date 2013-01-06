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
package br.com.objectos.comuns.cli;

import com.beust.jcommander.JCommander;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractCommand<O extends Options> implements Command {

  @Override
  public final void execute(Args args) {
    O options = parseOptions(args);

    executeWithOptions(options);
  }

  protected abstract void executeWithOptions(O options);

  protected abstract O getNewOptions();

  private O parseOptions(Args args) {
    O options = getNewOptions();
    new JCommander(options, args.asArray());
    return options;
  }

}