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
package br.com.objectos.comuns.sitebricks;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractEntityFilter<T> implements Filter {

  @Override
  public final void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;

    String uri = request.getRequestURI();
    String contextPath = request.getContextPath();
    uri = uri.substring(contextPath.length());

    T entity = parseEntity(uri);

    Context<T> context = new Context<T>(request, response, chain, uri, entity);

    if (shouldFilter(context)) {

      if (entity != null) {

        onSuccess(context);

      } else {

        onEntityNotFound(context);

      }

    } else {

      chain.doFilter(request, response);

    }

  }

  protected abstract T parseEntity(String uri);

  protected abstract boolean shouldFilter(Context<T> context);

  protected abstract void onSuccess(Context<T> context) throws IOException, ServletException;

  protected void onEntityNotFound(Context<T> context) throws IOException, ServletException {

    HttpServletResponse response = context.getResponse();
    response.sendError(entityNotFoundErrorCode());

  }

  protected int entityNotFoundErrorCode() {
    return HttpServletResponse.SC_NOT_FOUND;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void destroy() {
  }

  protected static class Context<T> {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final FilterChain chain;
    private final String uri;
    private final T entity;

    public Context(HttpServletRequest request,
                   HttpServletResponse response,
                   FilterChain chain,
                   String uri,
                   T entity) {
      this.request = request;
      this.response = response;
      this.chain = chain;
      this.uri = uri;
      this.entity = entity;
    }

    public void store(Class<?> key, Object value) {
      request.setAttribute(key.getName(), value);
    }

    public void setAttribute(String name, Object o) {
      request.setAttribute(name, o);
    }

    public void sendError(int code) throws IOException {
      response.sendError(code);
    }

    public void proceed() throws IOException, ServletException {
      chain.doFilter(request, response);
    }

    public HttpServletRequest getRequest() {
      return request;
    }

    public HttpServletResponse getResponse() {
      return response;
    }

    public FilterChain getChain() {
      return chain;
    }

    public String getUri() {
      return uri;
    }

    public T getEntity() {
      return entity;
    }

  }

}