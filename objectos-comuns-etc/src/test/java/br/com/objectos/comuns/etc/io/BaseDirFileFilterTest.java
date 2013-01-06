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
package br.com.objectos.comuns.etc.io;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class BaseDirFileFilterTest {

  private BaseDirFileFilter filter;

  private File templatesDir;
  private File destinationDir;

  @BeforeClass
  public void setUp() {
    templatesDir = new File("/whatever/templates");
    destinationDir = new File("/tmp");
    filter = new TestFilter(destinationDir);
  }

  public void normal_case() {
    String res = applyTo("/simple.txt");
    assertThat(res, equalTo("/tmp/simple.txt"));
  }

  public void normal_case_subdirs() {
    String res = applyTo("/sub1/sub2/sub3/simple.txt");
    assertThat(res, equalTo("/tmp/sub1/sub2/sub3/simple.txt"));
  }

  public void prefix_package() {
    String res = applyTo("/src/main/java/prefix-package/whatever.txt");
    assertThat(res, equalTo("/tmp/src/main/java/replace-package/whatever.txt"));
  }

  public void prefix_shortname() {
    String res = applyTo("/src/main/webapp/prefix-shortname-js/my.js");
    assertThat(res, equalTo("/tmp/src/main/webapp/replace-shortname-js/my.js"));
  }

  private String applyTo(String filename) {
    File template = new File(templatesDir, filename);
    return filter.apply(templatesDir, template).getAbsolutePath();
  }

  private class TestFilter extends BaseDirFileFilter {

    public TestFilter(File destinationDir) {
      super(destinationDir);
    }

    @Override
    protected String filterDestinationBaseDir(String input) {
      if (input.contains("prefix-package")) {
        return input.replaceAll("prefix-package", "replace-package");

      } else if (input.contains("prefix-shortname")) {
        return input.replaceAll("prefix-shortname", "replace-shortname");

      } else {
        return input;

      }
    }

  }

}