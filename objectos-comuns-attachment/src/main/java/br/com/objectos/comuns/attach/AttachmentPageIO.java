/*
 * AttachmentPageIO.java criado em 21/10/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import java.util.UUID;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class AttachmentPageIO {

  private AttachmentPageIO() {
  }

  public static AttachmentPage load(String baseDir, UUID uuid, int number) {
    return new Builder(baseDir, uuid, number).build();
  }

  private static class Builder implements AttachmentPage.Builder,
      br.com.objectos.comuns.base.Builder<AttachmentPage> {

    private final String baseDir;

    private final UUID uuid;

    private final int number;

    public Builder(String baseDir, UUID uuid, int number) {
      this.baseDir = baseDir;
      this.uuid = uuid;
      this.number = number;
    }

    @Override
    public AttachmentPage build() {
      return new AttachmentPageImpl(this);
    }

    @Override
    public String getBaseDir() {
      return baseDir;
    }

    @Override
    public UUID getUuid() {
      return uuid;
    }

    @Override
    public int getNumber() {
      return number;
    }

  }

}