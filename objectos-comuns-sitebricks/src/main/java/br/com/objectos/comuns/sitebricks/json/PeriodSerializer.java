/*
 * PeriodSerializer.java criado em 30/09/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.Period;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class PeriodSerializer extends JsonSerializer<Period> {

  @Override
  public void serialize(Period value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {

    jgen.writeString(value.toString());

  }

}