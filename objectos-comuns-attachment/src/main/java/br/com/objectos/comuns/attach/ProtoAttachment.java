/*
 * AnexoJdbc.java criado em 06/11/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ProtoAttachment implements Attachment {

  private final String baseDir;

  private final UUID uuid;

  private final String name;

  private final Mime mime;

  public ProtoAttachment(Builder builder) {
    File baseDirFile = builder.getBaseDir();
    baseDir = baseDirFile.getAbsolutePath();
    uuid = builder.getUuid();
    mime = builder.getMime();
    name = builder.getName();
  }

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public Exception getCause() {
    throw new IllegalStateException("This is a valid attachment");
  }

  @Override
  public void delete() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getBaseDir() {
    return baseDir;
  }

  @Override
  public File getFile() {
    File dir = AttachmentIO.dir(baseDir, uuid);
    return new File(dir, name);
  }

  @Override
  public UUID getUuid() {
    return uuid;
  }

  @Override
  public Mime getMime() {
    return mime;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<AttachmentPage> getPages() {
    throw new UnsupportedOperationException();
  }

}