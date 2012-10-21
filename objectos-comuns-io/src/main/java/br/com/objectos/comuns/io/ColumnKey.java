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
package br.com.objectos.comuns.io;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ColumnKey<T> {

  private final Class<T> type;

  private final String name;

  public ColumnKey(Class<T> type, String name) {
    this.type = Preconditions.checkNotNull(type, "type");
    this.name = name;
  }

  public Class<T> getType() {
    return type;
  }

  public static <T> ColumnKey<T> of(Class<T> type) {
    return new ColumnKey<T>(type, null);
  }

  public static <T> ColumnKey<T> of(Class<T> type, String name) {
    return new ColumnKey<T>(type, name);
  }

  @Override
  public String toString() {
    return Joiner.on("=").skipNulls().join("ColumnKey", type, name);
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof ColumnKey)) {
      return false;
    }
    @SuppressWarnings("rawtypes")
    ColumnKey other = (ColumnKey) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }
    return true;
  }

}