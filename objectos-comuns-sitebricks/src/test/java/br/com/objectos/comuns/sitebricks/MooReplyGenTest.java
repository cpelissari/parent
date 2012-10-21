/*
 * TesteDeMooReplyGen.java criado em 26/08/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class MooReplyGenTest {

  private MooReplyGen replyGen;

  private boolean mootoolAjax;
  private String html;

  @BeforeClass
  public void prepareResolver() {
    RequestReflection requestReflection = new FakeRequestReflection();
    Mvel mvel = new FakeMvelRunner();

    replyGen = new MooReplyGenGuice(mvel, requestReflection);
  }

  @BeforeMethod
  public void reset() {
    mootoolAjax = false;
    html = null;
  }

  public void mootoolsAjaxsRequestShouldReturnOnlyBdTagContents() {
    Object context = new Object();

    mootoolAjax = true;
    html = "<html><div id=\"bd\"><p>Hello World!</p></div></html>";
    MooReply result = replyGen.get(context);

    assertTrue(result.ajax());
    assertThat(result.html(), equalTo("<p>Hello World!</p>"));
  }

  public void normalGetRequestShouldRetunFullTemplate() {
    Object context = new Object();

    mootoolAjax = false;
    html = "<html><div id=\"bd\"><p>Hello World!</p></div></html>";
    MooReply result = replyGen.get(context);

    assertFalse(result.ajax());
    assertThat(result.html(), startsWith("<html>"));
  }

  private class FakeRequestReflection implements RequestReflection {
    @Override
    public boolean isMootoolsAjax() {
      return mootoolAjax;
    }
  }

  private class FakeMvelRunner implements Mvel {
    @Override
    public String render(Object ctx) {
      return html;
    }
  }

}