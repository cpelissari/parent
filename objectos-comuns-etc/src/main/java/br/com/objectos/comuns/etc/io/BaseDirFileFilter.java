/*
 * BaseDirFileFilter.java criado em 04/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc.io;

import java.io.File;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class BaseDirFileFilter implements FileFilter {

  private final File destinationDir;

  public BaseDirFileFilter(File destinationDir) {
    this.destinationDir = destinationDir;
  }

  @Override
  public File apply(File templateBaseDir, File template) {
    File file = getDestinationFile(templateBaseDir, template);
    File parentFile = file.getParentFile();
    parentFile.mkdirs();
    return file;
  }

  protected File getDestinationFile(File templateBaseDir, File template) {
    String baseDir = getDestinationBaseDir(templateBaseDir, template);
    String fileName = getDestinationFileName(template);

    return new File(destinationDir, baseDir + '/' + fileName);
  }

  protected String getDestinationBaseDir(File templatesBaseDir, File template) {
    String templatesDir = templatesBaseDir.getAbsolutePath();

    String parent = template.getParent();
    int beginIndex = parent.indexOf(templatesDir);

    String baseDir = parent.substring(beginIndex + templatesDir.length());
    return filterBaseDirIfNecessary(baseDir);
  }

  protected String getDestinationFileName(File template) {
    return template.getName();
  }

  protected String filterDestinationBaseDir(String input) {
    return input;
  }

  private String filterBaseDirIfNecessary(String baseDir) {
    Iterable<String> parts = Splitter.on("/").split(baseDir);
    Iterable<String> filtered = Iterables.transform(parts, new Filter());
    return Joiner.on("/").join(filtered);
  }

  private class Filter implements Function<String, String> {
    @Override
    public String apply(String input) {
      return filterDestinationBaseDir(input);
    }
  }

}