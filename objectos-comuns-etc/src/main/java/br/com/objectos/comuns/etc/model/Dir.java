/*
 * Dir.java criado em 04/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc.model;

import java.io.File;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Dir {

  private final String path;

  private final transient File file;

  public Dir(String path) {
    this.path = path;
    this.file = new File(path);
    this.file.mkdirs();
    Preconditions.checkArgument(this.file.isDirectory(), "Path must point to a directory");
  }

  public String getPath() {
    return path;
  }

  public File getFile() {
    return file;
  }

  public File getFileAt(String path) {
    return new File(file, path);
  }

  @Override
  public String toString() {
    return path;
  }

}