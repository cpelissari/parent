/*
 * AttachmentInvalid.java criado em 19/08/2012
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
class InvalidAttachment implements Attachment {

  private final Exception e;

  public InvalidAttachment(Exception e) {
    this.e = e;
  }

  @Override
  public boolean isValid() {
    return false;
  }

  @Override
  public Exception getCause() {
    return e;
  }

  @Override
  public void delete() {
    throw new IllegalStateException("This is an invalid attachment");
  }

  @Override
  public String getBaseDir() {
    throw new IllegalStateException("This is an invalid attachment");
  }

  @Override
  public File getFile() {
    throw new IllegalStateException("This is an invalid attachment");
  }

  @Override
  public UUID getUuid() {
    throw new IllegalStateException("This is an invalid attachment");
  }

  @Override
  public Mime getMime() {
    throw new IllegalStateException("This is an invalid attachment");
  }

  @Override
  public String getName() {
    throw new IllegalStateException("This is an invalid attachment");
  }

  @Override
  public List<AttachmentPage> getPages() {
    throw new IllegalStateException("This is an invalid attachment");
  }

}