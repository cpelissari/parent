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

import com.google.sitebricks.SitebricksModule;
import com.google.sitebricks.SitebricksServletModule;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class AbstractModuleUI extends SitebricksModule {

  private final StageUI stage;

  public AbstractModuleUI(StageUI stage) {
    this.stage = stage;
  }

  @Override
  protected final void configureSitebricks() {
    bind(StageUI.class).toInstance(stage);

    bindCommon();
    bindApi();
    bindBricks();
    bindPages();
  }

  protected abstract void bindCommon();
  protected abstract void bindApi();
  protected abstract void bindBricks();
  protected abstract void bindPages();

  protected void preFilters(ExtendedServletModule m) {
  }
  protected void postFilters(ExtendedServletModule m) {
  }
  protected void customServlets(ExtendedServletModule m) {
  }

  protected boolean isStageDevelopment() {
    return StageUI.DEVELOPMENT.equals(stage);
  }

  protected boolean isStageProduction() {
    return StageUI.PRODUCTION.equals(stage);
  }

  @Override
  protected final SitebricksServletModule servletModule() {
    return new ExtendedServletModule();
  }

  public class ExtendedServletModule extends SitebricksServletModule {

    @Override
    protected void configurePreFilters() {
      preFilters(this);
    }

    @Override
    protected void configurePostFilters() {
      postFilters(this);
    }

    @Override
    protected void configureCustomServlets() {
      customServlets(this);
    }

    public FilterKeyBindingBuilder preFilter(String uriPattern, String... morePatterns) {
      return filter(uriPattern, morePatterns);
    }
    public FilterKeyBindingBuilder preFilterRegex(String regex, String... regexes) {
      return filterRegex(regex, regexes);
    }
    public FilterKeyBindingBuilder postFilter(String uriPattern, String... morePatterns) {
      return filter(uriPattern, morePatterns);
    }
    public FilterKeyBindingBuilder postFilterRegex(String regex, String... regexes) {
      return filterRegex(regex, regexes);
    }
    public ServletKeyBindingBuilder customServe(String urlPattern, String... morePatterns) {
      return serve(urlPattern, morePatterns);
    }
    public ServletKeyBindingBuilder customServeRegex(String regex, String... regexes) {
      return serve(regex, regexes);
    }

  }

}