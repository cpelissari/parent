/*
 * Copyright 2011 Objectos, Fábrica de Software LTDA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package br.com.objectos.comuns.io;

import static com.google.common.collect.Lists.newArrayList;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class TailFileReaderTest {

  private static final String SEPARATOR = System.getProperty("line.separator");

  private TailFileReader reader;

  private List<String> lines;

  @BeforeMethod
  public void reset() {
    reader = null;
    lines = newArrayList();
  }

  @AfterMethod(alwaysRun = true)
  public void cleanup() {
    if (reader != null) {
      reader.cancel();
    }
  }

  public void changesMadeToUnderlyingFileShouldPopulateLines()
      throws IOException, InterruptedException {

    File file = File.createTempFile("objectos-comuns-tail-listener", ".txt");
    reader = new TailFileReader(file);
    reader.addListener(new TailFileListenerImpl());
    reader.start();

    Thread.sleep(300);

    String line0 = UUID.randomUUID().toString();
    String line1 = UUID.randomUUID().toString();
    String line2 = UUID.randomUUID().toString();

    Files.append(br(line0), file, Charsets.UTF_8);
    Files.append(br(line1), file, Charsets.UTF_8);
    Files.append(br(line2), file, Charsets.UTF_8);

    Thread.sleep(300);

    assertEquals(lines.size(), 3);
    assertEquals(lines.get(0), line0);
    assertEquals(lines.get(1), line1);
    assertEquals(lines.get(2), line2);
  }

  public void tailOnlyShouldSkipFirstLines() throws Exception {
    String line0 = UUID.randomUUID().toString();
    String line1 = UUID.randomUUID().toString();
    String line2 = UUID.randomUUID().toString();
    String line3 = UUID.randomUUID().toString();
    String line4 = UUID.randomUUID().toString();

    File file = File.createTempFile("objectos-comuns-tail-listener", ".txt");
    Files.append(br(line0), file, Charsets.UTF_8);
    Files.append(br(line1), file, Charsets.UTF_8);
    Files.append(br(line2), file, Charsets.UTF_8);

    reader = new TailFileReader(file);
    reader.addListener(new TailFileListenerImpl());
    reader.tailOnly();
    reader.start();

    Thread.sleep(300);

    Files.append(br(line3), file, Charsets.UTF_8);
    Files.append(br(line4), file, Charsets.UTF_8);

    Thread.sleep(300);

    assertEquals(lines.size(), 2);
    assertEquals(lines.get(0), line3);
    assertEquals(lines.get(1), line4);
  }

  private String br(String line) {
    return line + SEPARATOR;
  }

  private class TailFileListenerImpl implements TailFileListener {

    @Override
    public void onTail(TailFileEvent event) {
      String line = event.getLine();
      lines.add(line);
    }

  }

}