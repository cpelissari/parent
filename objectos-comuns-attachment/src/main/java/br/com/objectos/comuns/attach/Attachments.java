/*
 * AnexoGen.java criado em 06/11/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import java.io.File;
import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@ImplementedBy(AttachmentsGuice.class)
public interface Attachments {

  interface SaveTo {

    List<Attachment> andSaveTo(File baseDir);

  }

  SaveTo getFromRequest();

}