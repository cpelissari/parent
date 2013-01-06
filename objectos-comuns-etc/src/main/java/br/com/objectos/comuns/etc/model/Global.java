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
public class Global {

  public static interface Builder extends br.com.objectos.comuns.base.Builder<Global> {
    User getUser();
    Dirs getDirs();
  }

  private final User user;

  private final Dirs dirs;

  Global(Builder builder) {
    user = builder.getUser();
    dirs = builder.getDirs();
  }

  public User getUser() {
    return user;
  }

  public Dirs getDirs() {
    return dirs;
  }

}