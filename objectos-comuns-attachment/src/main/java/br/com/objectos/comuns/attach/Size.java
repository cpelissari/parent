/*
 * Size.java criado em 04/10/2012
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.attach;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Size {

  private static final Size ORIGINAL = new Size(Integer.MAX_VALUE, Method.ULTRA_QUALITY);

  private final int width;

  private final Method method;

  private Size(int width, Method method) {
    this.width = width;
    this.method = method;
    Preconditions.checkArgument(width > 0, "width must be positive");
    Preconditions.checkNotNull(method, "method must be defined");
  }

  public static Size original() {
    return ORIGINAL;
  }
  public static Size widthOf(int width) {
    return new Size(width, Method.AUTOMATIC);
  }
  public static Size widthOf(int width, Method method) {
    return new Size(width, method);
  }

  static boolean isOriginal(Size size) {
    return ORIGINAL == size;
  }

  public int getWidth() {
    return width;
  }

  public Method getMethod() {
    return method;
  }

}