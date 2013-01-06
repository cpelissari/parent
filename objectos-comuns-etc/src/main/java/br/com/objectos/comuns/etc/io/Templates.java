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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.mvel2.templates.TemplateRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Templates {

  private static final Logger logger = LoggerFactory.getLogger(Templates.class);

  private final File templateBaseDir;

  private final FileFilter fileFilter;

  private final Object model;

  private Templates(File templateBaseDir, FileFilter fileFilter, Object model) {
    this.templateBaseDir = templateBaseDir;
    this.fileFilter = Preconditions.checkNotNull(fileFilter, "fileFilter");
    this.model = Preconditions.checkNotNull(model, "model");
  }

  public static Builder foundAtBaseDir(File templateBaseDir) {
    return new Builder(templateBaseDir);
  }

  public void parseAll() {
    parseAllDir(templateBaseDir);
  }

  public File parse(File template) {
    try {
      Preconditions.checkArgument(template.isFile());
      String text = templateToString(template, model);

      File dest = destinationFileOf(template);

      Files.write(text, dest, Charsets.UTF_8);

      return dest;
    } catch (IOException e) {
      logger.error("Error trying to write result file: " + template, e);
      throw new TemplateException(e);
    }
  }

  private void parseAllDir(File dir) {
    for (File child : dir.listFiles()) {
      if (child.isDirectory()) {
        parseAllDir(child);

      } else {
        parse(child);

      }
    }
  }

  private String templateToString(File template, Object model) {
    try {
      FileInputStream templateStream = Files.newInputStreamSupplier(template).getInput();
      Object result = TemplateRuntime.eval(templateStream, model);
      return result.toString();
    } catch (IOException e) {
      logger.error("Could not parse template file: " + template, e);
      throw new TemplateException(e);
    }
  }

  private File destinationFileOf(File template) {
    return fileFilter.apply(templateBaseDir, template);
  }

  public static class Builder implements br.com.objectos.comuns.base.Builder<Templates> {

    private final File templateBaseDir;

    private FileFilter fileFilter;

    private Object model;

    private Builder(File templateBaseDir) {
      this.templateBaseDir = Preconditions.checkNotNull(templateBaseDir);
    }

    @Override
    public Templates build() {
      return new Templates(templateBaseDir, fileFilter, model);
    }

    public Builder filterFilesWith(FileFilter fileFilter) {
      this.fileFilter = fileFilter;
      return this;
    }

    public Builder withModel(Object model) {
      this.model = model;
      return this;
    }

  }

}