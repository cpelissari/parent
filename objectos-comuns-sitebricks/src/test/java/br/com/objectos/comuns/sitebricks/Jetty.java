/*
 * Jetty.java criado em 03/11/2011
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.comuns.sitebricks;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler.Context;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.component.LifeCycle.Listener;
import org.eclipse.jetty.webapp.WebAppContext;

import com.google.inject.Injector;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Jetty {

  private static final String APP_NAME = "/";

  private final int port;

  private final Server server;

  private Injector injector;

  public Jetty(String path) {
    this(new WebAppContext(path, APP_NAME), new JettyPort().getPort());
  }

  public Jetty(WebAppContext webAppContext, int port) {
    this.port = port;
    this.server = new Server(port);

    webAppContext.addLifeCycleListener(new WebAppContextListener());
    this.server.setHandler(webAppContext);
  }

  public String getBaseUrl() {
    return String.format("http://localhost:%d", port);
  }

  public Injector getInjector() {
    return injector;
  }

  public int getPort() {
    return port;
  }

  public void start() throws Exception {
    server.start();
  }

  public void join() throws Exception {
    server.join();
  }

  public void stop() throws Exception {
    server.stop();
  }

  private class WebAppContextListener implements Listener {

    @Override
    public void lifeCycleStarted(LifeCycle event) {
      WebAppContext context = (WebAppContext) event;
      Context servletContext = context.getServletContext();
      injector = (Injector) servletContext.getAttribute(Injector.class.getName());
    }

    @Override
    public void lifeCycleStarting(LifeCycle event) {
    }

    @Override
    public void lifeCycleFailure(LifeCycle event, Throwable cause) {
    }

    @Override
    public void lifeCycleStopping(LifeCycle event) {
    }

    @Override
    public void lifeCycleStopped(LifeCycle event) {
    }

  }

}