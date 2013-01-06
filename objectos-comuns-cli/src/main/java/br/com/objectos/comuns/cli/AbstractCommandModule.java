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

import java.lang.annotation.Annotation;

import com.google.inject.PrivateModule;
import com.google.inject.multibindings.MapBinder;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractCommandModule extends PrivateModule {

  @Override
  protected final void configure() {
    bind(CommandExecutor.class).annotatedWith(getAnnotation()).to(CommandExecutorGuice.class);
    expose(CommandExecutor.class).annotatedWith(getAnnotation());

    DefaultCommandKey defaultCommandKey = new DefaultCommandKey(getDefaultKey());
    bind(DefaultCommandKey.class).toInstance(defaultCommandKey);

    MapBinder<CommandKey, Command> commands = MapBinder
        .newMapBinder(binder(), CommandKey.class, Command.class);

    bindCommands(commands);
  }

  protected abstract Class<? extends Annotation> getAnnotation();

  protected abstract CommandKey getDefaultKey();

  protected abstract void bindCommands(MapBinder<CommandKey, Command> commands);

}