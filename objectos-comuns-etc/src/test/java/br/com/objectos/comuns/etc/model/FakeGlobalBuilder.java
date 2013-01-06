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
package br.com.objectos.comuns.etc.model;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class FakeGlobalBuilder implements Global.Builder {

  private User user;

  private Dirs dirs;

  @Override
  public Global build() {
    return new Global(this);
  }

  public FakeGlobalBuilder user(User user) {
    this.user = user;
    return this;
  }

  public FakeGlobalBuilder dirs(Dirs dirs) {
    this.dirs = dirs;
    return this;
  }

  @Override
  public User getUser() {
    return user;
  }

  @Override
  public Dirs getDirs() {
    return dirs;
  }

}