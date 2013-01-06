/*
 * ScalarTest.java criado em 07/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { EtcTestModule.class })
public class ScalarTest {

  @Inject
  private Provider<Config> configProvider;

  public void single() throws IOException {
    String text = EtcFiles.readLines("/scalar-single.yaml");

    Config config = configProvider.get();
    Mapping res = config.load(text);

    assertThat(res.getString("string"), equalTo("abc"));
    assertThat(res.getBoolean("boolean"), equalTo(true));
    assertThat(res.getInt("int"), equalTo(123));
    assertThat(res.getLong("long"), equalTo(9223372036854775807l));
    assertThat(res.getFloat("float"), equalTo(1.23f));
    assertThat(res.getDouble("double"), equalTo(4.56d));

    List<Mapping> seqScalar = res.getSequence("seq-scalar");
    assertThat(seqScalar.size(), equalTo(3));
  }

  public void many() throws IOException {
    String text = EtcFiles.readLines("/scalar-many.yaml");

    Config config = configProvider.get();
    List<Mapping> res = config.loadAll(text);

    assertThat(res.size(), equalTo(2));

    Mapping r0 = res.get(0);
    assertThat(r0.getString("string"), equalTo("abc"));
    assertThat(r0.getBoolean("boolean"), equalTo(true));
    assertThat(r0.getInt("int"), equalTo(123));
    assertThat(r0.getLong("long"), equalTo(9223372036854775807l));
    assertThat(r0.getFloat("float"), equalTo(1.23f));
    assertThat(r0.getDouble("double"), equalTo(4.56d));
  }

}
