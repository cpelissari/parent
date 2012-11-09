/*
 * FinalAttachment.java criado em 19/08/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import java.io.File;
import java.util.List;
import java.util.UUID;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class FinalAttachment implements Attachment {

  private final Attachment delegate;

  private final List<AttachmentPage> pages;

  public FinalAttachment(Attachment delegate, List<AttachmentPage> pages) {
    Preconditions.checkArgument(delegate.isValid());
    this.delegate = delegate;
    this.pages = pages;
  }

  @Override
  public boolean isValid() {
    return delegate.isValid();
  }

  @Override
  public Exception getCause() {
    return delegate.getCause();
  }

  @Override
  public void delete() {
    delegate.delete();
  }

  @Override
  public String getBaseDir() {
    return delegate.getBaseDir();
  }

  @Override
  public File getFile() {
    return delegate.getFile();
  }

  @Override
  public UUID getUuid() {
    return delegate.getUuid();
  }

  @Override
  public Mime getMime() {
    return delegate.getMime();
  }

  @Override
  public String getName() {
    return delegate.getName();
  }

  @Override
  public List<AttachmentPage> getPages() {
    return pages;
  }

}