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

import static com.google.common.collect.Sets.newHashSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class TailFileReader {

  private final File file;

  private final Set<TailFileListener> listeners = newHashSet();

  private final long timeout = 30 * 60 * 1000; // 30 minutos

  private long startTime;

  private boolean cancelled = false;

  private boolean tailOnly = false;

  public TailFileReader(File file) {
    this.file = file;
  }

  public void addListener(TailFileListener listener) {
    listeners.add(listener);
  }

  public void cancel() {
    cancelled = true;
    listeners.clear();
  }

  public void start() {
    TailRunner runner = new TailRunner();
    new Thread(runner).start();
  }

  public void start(long millis) {
    TailRunner runner = new TailRunner(millis);
    new Thread(runner).start();
  }

  public void tailOnly() {
    tailOnly = true;
  }

  private class TailRunner implements Runnable {

    private final long millis;

    public TailRunner() {
      this(0);
    }

    public TailRunner(long millis) {
      this.millis = millis;
    }

    @Override
    public void run() {
      run0(millis);
    }

  }

  private void run0(long millis) {
    startTime = System.currentTimeMillis();

    BufferedReader reader = null;
    try {
      Thread.sleep(millis);

      FileReader in = new FileReader(file);
      reader = new BufferedReader(in);

      if (tailOnly) {
        long length = file.length();
        reader.skip(length);
      }

      while (active()) {
        String line = reader.readLine();

        if (line != null) {
          fireEvent(line);
        } else {
          Thread.sleep(200);
        }
      }
    } catch (FileNotFoundException e) {
      throw new TailFileException(e);
    } catch (IOException e) {
      throw new TailFileException(e);
    } catch (InterruptedException e) {
      throw new TailFileException(e);
    } finally {
      closeReader(reader);
    }
  }

  private boolean active() {
    long runTime = System.currentTimeMillis() - startTime;
    return !cancelled && runTime < timeout;
  }

  private void closeReader(BufferedReader reader) {
    if (reader != null) {
      try {
        reader.close();
      } catch (IOException e) {
        // ?
      }
    }
  }

  private void fireEvent(String line) {
    TailFileEvent event = new TailFileEvent(this, line);

    for (TailFileListener listener : listeners) {
      listener.onTail(event);
    }
  }

}