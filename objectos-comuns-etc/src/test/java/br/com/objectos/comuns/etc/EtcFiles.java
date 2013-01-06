/*
 * Copyright 2012 Objectos, Fábrica de Software LTDA.
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
package br.com.objectos.comuns.etc;

import static com.google.common.collect.Maps.newTreeMap;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import com.google.common.io.Resources;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class EtcFiles {

  private EtcFiles() {
  }

  public static File cleanFile(File parent, String filename) {
    File file = new File(parent, filename);
    file.delete();
    return file;
  }

  public static void rm_rf(File baseDir) {
    Preconditions.checkArgument(baseDir.isDirectory());

    for (File child : baseDir.listFiles()) {
      if (child.isDirectory()) {
        rm_rf(child);
      } else {
        child.delete();
      }
    }
  }

  public static String readAllLines(File baseDir) throws IOException {
    Map<String, String> map = newTreeMap();
    readAllLines0(map, baseDir);
    Collection<String> values = map.values();
    return values.toString();
  }
  public static String readAllLines(String filename)
      throws IOException, URISyntaxException {
    URL url = Resources.getResource(EtcFiles.class, filename);
    File file = new File(url.toURI());
    return readAllLines(file);
  }

  public static String readLines(String filename) throws IOException {
    URL url = Resources.getResource(EtcFiles.class, filename);
    return Resources.toString(url, Charsets.UTF_8);
  }

  public static String readLines(File file) throws IOException {
    return Files.toString(file, Charsets.UTF_8);
  }

  private static void readAllLines0(Map<String, String> map, File baseDir) throws IOException {
    Preconditions.checkArgument(baseDir.isDirectory());

    for (File child : baseDir.listFiles()) {
      if (child.isDirectory()) {
        readAllLines0(map, child);
      } else {
        String lines = EtcFiles.readLines(child);
        map.put(child.getAbsolutePath(), lines);
      }
    }
  }

}