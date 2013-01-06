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

import java.util.Map;

import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class CommandExecutorGuice implements CommandExecutor {

  private final Map<CommandKey, Command> commandMap;

  private final DefaultCommandKey defaultCommandKey;

  @Inject
  public CommandExecutorGuice(Map<CommandKey, Command> commandMap,
                              DefaultCommandKey defaultCommandKey) {
    this.commandMap = commandMap;
    this.defaultCommandKey = defaultCommandKey;
  }

  @Override
  public void execute(Args args) {
    CommandKey commandKey = defaultCommandKey.get();

    for (CommandKey key : commandMap.keySet()) {
      if (args.contains(key.getName())) {
        commandKey = key;
        break;
      }
    }

    Command command = commandMap.get(commandKey);
    command.execute(args.remove(commandKey.getName()));
  }

}