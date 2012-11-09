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

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author endo
 * 
 * @version $Id$
 */
public class Hashs {

  public static String withMD5(String input) {
    try {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.update(input.getBytes("UTF-8"), 0, input.length());
      byte[] bytes = digest.digest();
      String md5sum = new BigInteger(1, bytes).toString(16);
      return appendZeroIfNecessaryTo(md5sum);
    } catch (Exception e) {
      throw new HashException(e);
    }
  }

  private static String appendZeroIfNecessaryTo(String checksum) {
    int length = checksum.length();
    if (length % 2 != 0) {
      return "0" + checksum;
    } else {
      return checksum;
    }
  }

}