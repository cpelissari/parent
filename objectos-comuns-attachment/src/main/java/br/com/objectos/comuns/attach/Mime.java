/*
 * Mime.java criado em 04/12/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import br.com.objectos.comuns.web.upload.UploadedFile;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public enum Mime {

  GIF("image/gif"),
  JPEG("image/jpeg"),
  JPG("image/jpeg"),
  PDF("application/pdf"),
  PNG("image/png");

  private final String type;

  private Mime(String type) {
    this.type = type;
  }

  public static Mime valueOf(UploadedFile file) {
    String name = file.getName();
    return valueOfContentType(name);
  }

  public static Mime valueOfContentType(String filename) {
    try {

      String[] parts = filename.split("\\.");
      String ext = parts[parts.length - 1];
      return Mime.valueOf(ext.toUpperCase());

    } catch (IllegalArgumentException e) {

      throw new AttachmentException("Attachment file extension '" + filename + "' not supported.");

    }
  }

  public String getType() {
    return type;
  }

}