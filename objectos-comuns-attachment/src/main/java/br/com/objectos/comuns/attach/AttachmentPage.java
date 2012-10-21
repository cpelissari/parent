/*
 * PaginaDeAnexo.java criado em 16/12/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import java.io.File;
import java.util.UUID;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public interface AttachmentPage {

  interface Builder {

    String getBaseDir();

    UUID getUuid();

    int getNumber();

  }

  void delete();

  byte[] toByteArray(Size size);
  byte[] toByteArray(SizeEnum enumValue);

  File getFile();

  UUID getUuid();

  int getNumber();

  String getName();

}