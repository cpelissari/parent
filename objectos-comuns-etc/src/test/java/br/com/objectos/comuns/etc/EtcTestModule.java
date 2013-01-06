/*
 * YamlTestModule.java criado em 04/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc;

import br.com.objectos.comuns.etc.model.Dirs;
import br.com.objectos.comuns.etc.model.DirsLoader;
import br.com.objectos.comuns.etc.model.Global;
import br.com.objectos.comuns.etc.model.GlobalLoader;
import br.com.objectos.comuns.etc.model.User;
import br.com.objectos.comuns.etc.model.UserLoader;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class EtcTestModule extends AbstractEtcModule {

  @Override
  protected void configureEtc() {
    etc(Global.class).withLoader(GlobalLoader.class);
    etc(User.class).withLoader(UserLoader.class);
    etc(Dirs.class).withLoader(DirsLoader.class);
  }

}