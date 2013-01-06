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

import static com.google.common.collect.Maps.newHashMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class ExecutorTest {

  private Executor executor;

  private String executedCommand;

  private String defaultKey;

  @BeforeClass
  public void setUp() {
    defaultKey = "whatever";
    DefaultMainCommandKey defaultMainCommandKey = new DefaultMainCommandKey(defaultKey);

    Map<String, MainCommand> commandMap = newHashMap();

    commandMap.put("comm0", new FakeMainCommand("comm0"));
    commandMap.put("comm1", new FakeMainCommand("comm1"));
    commandMap.put("comm2", new FakeMainCommand("comm2"));
    commandMap.put(defaultKey, new FakeMainCommand(defaultKey));

    executor = new ExecutorGuice(commandMap, defaultMainCommandKey);
  }

  @BeforeMethod
  public void reset() {
    executedCommand = null;
  }

  public void should_execute_correct_command() {
    String command = "comm0";

    executor.execute(command, "arg0", "arg1");

    assertThat(executedCommand, equalTo(command));
  }

  public void when_command_not_found_should_default_to_help_command() {
    String command = "xxx";

    executor.execute(command, "arg0", "arg1");

    assertThat(executedCommand, equalTo(defaultKey));
  }

  public void should_not_treat_prefix_args_as_command() {
    String command = "comm2";

    executor.execute("--verbose", command, "arg0", "arg1");

    assertThat(executedCommand, equalTo(command));
  }

  private class FakeMainCommand extends EmptyMainCommand {

    private final String command;

    public FakeMainCommand(String command) {
      this.command = command;
    }

    @Override
    public void execute(Args args) {
      executedCommand = command;
    }

  }

}