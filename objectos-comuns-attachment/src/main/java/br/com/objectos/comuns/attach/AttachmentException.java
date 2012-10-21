/*
 * ExcecaoDeAnexo.java criado em 04/12/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class AttachmentException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public AttachmentException(String message) {
    super(message);
  }

  public AttachmentException(String message, Throwable cause) {
    super(message, cause);
  }

}