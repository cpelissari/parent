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
package br.com.objectos.comuns.base;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Strings {

  private final String text;

  private Strings(String text) {
    this.text = text;
  }

  public static Strings alphanum(String value) {
    return n(value).alphanum();
  }

  public static Strings camelCase(String value) {
    return n(value).camelCase();
  }

  public static Strings accentsToAscii(String value) {
    return n(value).accentsToAscii();
  }

  public static Strings stripWhitespace(String value) {
    return n(value).stripWhitespace();
  }

  public static Strings whitespaceTo(String text, String replacement) {
    return n(text).whitespaceTo(replacement);
  }

  public Strings alphanum() {
    String alfanumerico = text.replaceAll("[^\\p{Alnum}|\\s]+", "");
    return n(alfanumerico);
  }

  public Strings camelCase() {
    StringBuilder camelCase = new StringBuilder();
    // Remove everything that is not alphanumeric or whitespace, then split on
    // word boundaries
    String[] tokens = text.split("\\s");

    for (int i = 0; i < tokens.length; i++) {
      String token = tokens[i].toLowerCase();
      // Append word, uppercase first letter of word
      if (token.length() > 1) {
        camelCase.append(token.substring(0, 1).toUpperCase());
        camelCase.append(token.substring(1));
      } else {
        camelCase.append(token.toUpperCase());
      }
    }

    return new Strings(camelCase.toString());
  }

  public Strings accentsToAscii() {
    String accentsReplaced = AsciiUtils.convertNonAscii(text);
    return new Strings(accentsReplaced);
  }

  public Strings stripWhitespace() {
    String semEspacos = text.replaceAll("[\\s]+", "");
    return new Strings(semEspacos);
  }

  public Strings whitespaceTo(String replacement) {
    String replaced = text.replaceAll("[\\s]+", replacement);
    return new Strings(replaced);
  }

  @Override
  public String toString() {
    return text;
  }

  private static Strings n(String value) {
    return new Strings(value);
  }

  private static class AsciiUtils {

    private static final String PLAIN_ASCII = "AaEeIiOoUu" // grave
        + "AaEeIiOoUuYy" // acute
        + "AaEeIiOoUuYy" // circumflex
        + "AaOoNn" // tilde
        + "AaEeIiOoUuYy" // umlaut
        + "Aa" // ring
        + "Cc" // cedilla
        + "OoUu" // double acute
    ;

    private static final String UNICODE = "\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9"
        + "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD"
        + "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177"
        + "\u00C3\u00E3\u00D5\u00F5\u00D1\u00F1"
        + "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF"
        + "\u00C5\u00E5" + "\u00C7\u00E7" + "\u0150\u0151\u0170\u0171";

    // private constructor, can't be instanciated!
    private AsciiUtils() {
    }

    // remove accentued from a string and replace with ascii equivalent
    public static String convertNonAscii(String s) {
      if (s == null)
        return null;
      StringBuilder sb = new StringBuilder();
      int n = s.length();
      for (int i = 0; i < n; i++) {
        char c = s.charAt(i);
        int pos = UNICODE.indexOf(c);
        if (pos > -1) {
          sb.append(PLAIN_ASCII.charAt(pos));
        } else {
          sb.append(c);
        }
      }
      return sb.toString();
    }

  }

}