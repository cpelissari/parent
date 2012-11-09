/*
 * PaginaDeAnexoGen.java criado em 16/12/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import com.google.inject.ImplementedBy;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@ImplementedBy(PagesGuice.class)
interface Pages {

  Attachment addPagesTo(Attachment attach);

}