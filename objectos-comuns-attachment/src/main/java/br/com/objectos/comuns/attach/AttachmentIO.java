/*
 * AnexoIO.java criado em 04/12/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.io.OutputSupplier;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class AttachmentIO {

  private AttachmentIO() {
  }

  public static File dir(String baseDir, UUID uuid) {
    File baseDirFile = new File(baseDir);
    return dir(baseDirFile, uuid);
  }

  public static File dir(File baseDir, UUID uuid) {
    Preconditions.checkArgument(baseDir.isDirectory(), "baseDir must be a valid directory");

    baseDir.mkdirs();

    String filename = uuid.toString();
    String prefix = filename.substring(0, 1);

    File res = new File(baseDir, prefix);
    res.mkdirs();

    return res;
  }

  static void write(File baseDir, UUID uuid, InputStream input)
      throws IOException {

    String filename = uuid.toString();
    File subdir = dir(baseDir, uuid);

    File res = new File(subdir, filename);
    OutputSupplier<FileOutputStream> out = Files.newOutputStreamSupplier(res);
    ByteStreams.copy(input, out);

  }

}