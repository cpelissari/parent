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
package br.com.objectos.comuns.sitebricks;

import static com.google.common.collect.Sets.newLinkedHashSet;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class RequestURIGrep {

  private final Set<Pattern> patternSet;

  private RequestURIGrep(Set<Pattern> patterns) {
    this.patternSet = ImmutableSet.copyOf(patterns);
  }

  public static Builder matchUris() {
    return new Builder();
  }

  public boolean matches(String uri) {
    Preconditions.checkArgument(uri.startsWith("/"), "Must be an uri starting with /");

    boolean res = false;

    for (Pattern pattern : patternSet) {
      Matcher matcher = pattern.matcher(uri);

      if (matcher.matches()) {
        res = true;
        break;
      }
    }

    return res;
  }

  public static class Builder {

    private final Set<Pattern> regexSet = newLinkedHashSet();

    public RequestURIGrep build() {
      return new RequestURIGrep(regexSet);
    }

    public Builder from(RequestURIGrep grep) {
      regexSet.addAll(grep.patternSet);
      return this;
    }

    /**
     * Entering /login will match /login but not /login/me
     */
    public Builder equalTo(String uri) {
      Preconditions.checkArgument(uri.startsWith("/"), "Must be an uri starting with /");
      return add(String.format("^%s$", uri));
    }

    /**
     * Entering .css will match any uri that has that suffix such as
     * /dir1/sheet.css or /sheet.css
     */
    public Builder endsWith(String ext) {
      Preconditions.checkArgument(ext.startsWith("."),
          "Must be file extension such as .css or .jpg");
      return add(String.format(".*\\%s$", ext));
    }

    /**
     * Entering /restricted will match /restricted and any 'sub-page' such as
     * /restricted/user1 and /restricted/secret-project.pdf
     */
    public Builder startsWith(String uri) {
      Preconditions.checkArgument(uri.startsWith("/"), "Must be an uri starting with /");
      Preconditions.checkArgument(!uri.endsWith("/"),
          "Enter the prefix only with the trailing slash");
      return add(String.format("^%s($|/.*)", uri));
    }

    private Builder add(String regex) {
      regexSet.add(Pattern.compile(regex));
      return this;
    }

  }

}