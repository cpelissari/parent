/*
 * TesteDeAnexoIO.java criado em 04/12/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.testng.annotations.Test;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class AttachmentIOTest {

  public void attachs_should_be_saved_spread_out_based_on_first_char_of_UUID() throws IOException {
    File tmp = Files.createTempDir();
    UUID uuid = UUID.randomUUID();
    String filename = uuid.toString();
    String prefix = filename.substring(0, 1);
    File expected = new File(tmp, prefix + "/" + filename);

    File baseDir = AttachmentIO.dir(tmp, uuid);
    File res = new File(baseDir, uuid.toString());

    assertThat(res, equalTo(expected));
  }

  public void attach_should_be_saved_correctly() throws IOException {
    File tmp = Files.createTempDir();
    UUID uuid = UUID.randomUUID();

    File baseDir = AttachmentIO.dir(tmp, uuid);
    File expected = new File(baseDir, uuid.toString());

    expected.delete();

    assertThat(expected.exists(), equalTo(false));

    byte[] bytes = new byte[] {
        'a', 'b', 'c' };
    InputSupplier<ByteArrayInputStream> supplier = ByteStreams.newInputStreamSupplier(bytes);
    InputStream in = supplier.getInput();

    AttachmentIO.write(tmp, uuid, in);

    assertThat(expected.exists(), equalTo(true));
  }

}