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

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Args {

  private final List<String> args;

  public Args(String[] args) {
    this.args = ImmutableList.copyOf(args);
  }

  private Args(List<String> args) {
    this.args = ImmutableList.copyOf(args);
  }

  public boolean contains(String o) {
    return args.contains(o);
  }

  public Args remove(String token) {
    List<String> list = newArrayList(args);
    list.remove(token);
    return new Args(list);
  }

  public String[] asArray() {
    return args.toArray(new String[] {});
  }

  public List<String> asList() {
    return args;
  }

}