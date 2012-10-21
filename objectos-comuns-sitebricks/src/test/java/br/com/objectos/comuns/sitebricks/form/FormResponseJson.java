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
package br.com.objectos.comuns.sitebricks.form;

import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class FormResponseJson {

  private Map<?, ?> context;
  private boolean valid;
  private List<ErrorJson> errors;
  private String redirectUrl;

  public Map<?, ?> getContext() {
    return context;
  }
  public boolean isValid() {
    return valid;
  }
  public List<ErrorJson> getErrors() {
    return errors;
  }
  public String getRedirectUrl() {
    return redirectUrl;
  }

  public void setContext(Map<?, ?> context) {
    this.context = context;
  }
  public void setValid(boolean valid) {
    this.valid = valid;
  }
  public void setErrors(List<ErrorJson> errors) {
    this.errors = errors;
  }
  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("valid", valid)
        .add("errors", errors)
        .add("redirectUrl", redirectUrl)
        .toString();
  }

  public static class ErrorJson implements Comparable<ErrorJson> {

    private String name;
    private String message;
    private String messageBody;

    public String getName() {
      return name;
    }
    public String getMessage() {
      return message;
    }
    public String getMessageBody() {
      return messageBody;
    }
    public void setName(String name) {
      this.name = name;
    }
    public void setMessage(String message) {
      this.message = message;
    }
    public void setMessageBody(String messageBody) {
      this.messageBody = messageBody;
    }

    @Override
    public int compareTo(ErrorJson o) {
      return name.compareTo(o.name);
    }

    @Override
    public String toString() {
      return Objects.toStringHelper("Error")
          .add("name", name)
          .add("message", message)
          .add("messageBody", messageBody)
          .toString();
    }

  }

}