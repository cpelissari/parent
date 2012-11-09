/*
 * Anexo.java criado em 06/11/2011
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
public interface Attachment {

  interface Builder extends br.com.objectos.comuns.base.Builder<Attachment> {

    File getBaseDir();

    UUID getUuid();

    Mime getMime();

    String getName();

  }

  boolean isValid();
  Exception getCause();

  void delete();

  String getBaseDir();

  File getFile();

  UUID getUuid();

  Mime getMime();

  String getName();

  List<AttachmentPage> getPages();

}