/*
 * MooReplyGen.java criado em 25/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import com.google.inject.ImplementedBy;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@ImplementedBy(MooReplyGenGuice.class)
public interface MooReplyGen {

  MooReply get(Object ctx);

}