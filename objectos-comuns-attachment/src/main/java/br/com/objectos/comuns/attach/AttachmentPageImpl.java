/*
 * PaginaDeAnexoJdbc.java criado em 16/12/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

import com.google.common.io.Files;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class AttachmentPageImpl implements AttachmentPage {

  private final Attachment attachment;
  private final int number;
  private final boolean original;

  public AttachmentPageImpl(Builder construtor) {
    attachment = construtor.getAttachment();
    number = construtor.getNumber();
    original = construtor.isOriginal();
  }

  @Override
  public void delete() {
    File file = getFile();
    file.delete();
  }

  @Override
  public byte[] toByteArray(Size size) {
    byte[] bytes = new byte[] {};

    File file = getFile();
    if (file != null) {
      bytes = image(size, file);
    }

    return bytes;
  }

  @Override
  public byte[] toByteArray(SizeEnum enumValue) {
    Size size = enumValue.asSize();
    return toByteArray(size);
  }

  @Override
  public File getFile() {
    String baseDir = attachment.getBaseDir();
    UUID uuid = attachment.getUuid();

    File dir = AttachmentIO.dir(baseDir, uuid);

    String name = getName();
    return new File(dir, name);
  }

  @Override
  public Attachment getAttachment() {
    return attachment;
  }

  @Override
  public UUID getUuid() {
    return attachment.getUuid();
  }

  @Override
  public int getNumber() {
    return number;
  }

  @Override
  public boolean isOriginal() {
    return original;
  }

  @Override
  public String getName() {
    UUID uuid = attachment.getUuid();

    if (!original) {
      return String.format("%s.%d", uuid, number);
    } else {
      return uuid.toString();
    }
  }

  private byte[] image(Size size, File file) {
    byte[] bytes = new byte[] {};

    try {
      if (!Size.isOriginal(size)) {

        BufferedImage img = ImageIO.read(file);
        BufferedImage thumb = Scalr.resize(
            img,
            size.getMethod().toScalr(),
            Mode.FIT_TO_WIDTH,
            size.getWidth(),
            Integer.MAX_VALUE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(thumb, "PNG", baos);
        bytes = baos.toByteArray();

      } else {

        bytes = Files.toByteArray(file);

      }

    } catch (IOException e) {
    }

    return bytes;
  }
}