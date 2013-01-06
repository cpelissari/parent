/*
 * DirTest.java criado em 04/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;

import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class DirTest {

  public void file_at_should_return_file_at_subdir() {
    Dir dir = new Dir("/tmp");

    assertThat(dir.getFile().exists(), is(true));

    File file = dir.getFileAt("/whatever/dummy.txt");
    assertThat(file.getAbsolutePath(), equalTo("/tmp/whatever/dummy.txt"));
  }

}