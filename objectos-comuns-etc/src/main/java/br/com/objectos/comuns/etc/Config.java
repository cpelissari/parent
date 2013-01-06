/*
 * Yaml.java criado em 04/09/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.etc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@ImplementedBy(SnakeYamlConfig.class)
public interface Config {

  Mapping load(Reader io);
  Mapping load(String yaml);
  Mapping load(InputStream input);

  <T> T load(Reader io, Class<T> type);
  <T> T load(String yaml, Class<T> type);
  <T> T load(InputStream input, Class<T> type);

  List<Mapping> loadAll(Reader io);
  List<Mapping> loadAll(String yaml);
  List<Mapping> loadAll(InputStream input);

  <T> List<T> loadAll(Reader io, Class<T> type);
  <T> List<T> loadAll(String yaml, Class<T> type);
  <T> List<T> loadAll(InputStream input, Class<T> type);

  void write(Object model, File file) throws IOException;

  String toString(Object model);

}