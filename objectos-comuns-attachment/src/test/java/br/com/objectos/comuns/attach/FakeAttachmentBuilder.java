/*
 * FakeAttachmentBuilder.java criado em 04/10/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import br.com.objectos.comuns.base.Builder;

import com.google.common.base.Throwables;
import com.google.common.io.Files;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class FakeAttachmentBuilder {

  private FakeAttachmentBuilder() {
  }

  public static InvalidBuilder invalid() {
    return new InvalidBuilder();
  }

  public static ValidBuilder valid() {
    return new ValidBuilder();
  }

  public static class InvalidBuilder implements Builder<Attachment> {

    private Exception cause;

    @Override
    public Attachment build() {
      return new InvalidAttachment(cause);
    }

    public InvalidBuilder cause(Exception cause) {
      this.cause = cause;
      return this;
    }

  }

  public static class ValidBuilder implements Builder<Attachment> {

    private File baseDir = Files.createTempDir();

    private File file;

    @Override
    public Attachment build() {
      try {

        Attachment proto = new ProtoBuilder().build();
        AttachmentIO.write(baseDir, proto.getUuid(), Files.newInputStreamSupplier(file).getInput());
        return new PagesGuice().addPagesTo(proto);

      } catch (IOException e) {

        throw Throwables.propagate(e);

      }
    }

    public ValidBuilder baseDir(File baseDir) {
      this.baseDir = baseDir;
      return this;
    }

    public ValidBuilder file(File file) {
      this.file = file;
      return this;
    }

    private class ProtoBuilder implements Attachment.Builder {

      @Override
      public Attachment build() {
        return new ProtoAttachment(this);
      }

      @Override
      public File getBaseDir() {
        return baseDir;
      }

      @Override
      public UUID getUuid() {
        return UUID.randomUUID();
      }

      @Override
      public Mime getMime() {
        return Mime.valueOfContentType(getName());
      }

      @Override
      public String getName() {
        return file != null ? file.getName() : "";
      }

    }

  }

}