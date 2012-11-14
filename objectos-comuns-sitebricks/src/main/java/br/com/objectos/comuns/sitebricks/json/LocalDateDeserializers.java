/*
 * LocalDateDeserializer.java criado em 15/10/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class LocalDateDeserializers {

  private LocalDateDeserializers() {
  }

  public static class ISO extends Deserializer {
    public ISO() {
      super("yyyy-MM-dd");
    }
  }

  public static class DD_MM_YY extends Deserializer {
    public DD_MM_YY() {
      super("dd/MM/yy");
    }
  }

  public static class DD_MM_YYYY extends Deserializer {
    public DD_MM_YYYY() {
      super("dd/MM/yyyy");
    }
  }

  public static class MM_DD_YYYY extends Deserializer {
    public MM_DD_YYYY() {
      super("MM/dd/yyyy");
    }
  }

  private static abstract class Deserializer extends JsonDeserializer<LocalDate> {

    private final DateTimeFormatter formatter;

    public Deserializer(String pattern) {
      this.formatter = DateTimeFormat.forPattern(pattern);
    }

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      LocalDate res = null;

      JsonToken t = jp.getCurrentToken();

      if (t == JsonToken.VALUE_STRING) {
        try {
          String text = jp.getText().trim();
          res = formatter.parseLocalDate(text);
        } catch (IllegalArgumentException iae) {

        }
      }

      return res;

    }

  }

}