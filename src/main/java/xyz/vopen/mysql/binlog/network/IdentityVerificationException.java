/*
 * Copyright 2016 VOPEN.CN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.vopen.mysql.binlog.network;

import javax.net.ssl.SSLException;

/**
 * 身份认证密码异常
 *
 * @author <a href="mailto:cinc.jan@gmail.com">CiNC</a>
 */
public class IdentityVerificationException extends SSLException {

    private static final long serialVersionUID = 1L;

    public IdentityVerificationException (String message) {
        super(message);
    }

}
