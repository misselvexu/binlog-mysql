/*
 * Copyright 2015 VOPEN.CN
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

import java.io.IOException;

/**
 * 服务端异常
 *
 * @author <a href="mailto:cinc.jan@gmail.com">CiNC</a>
 */
public class ServerException extends IOException {

    private static final long serialVersionUID = -5156174080307921834L;

    /**
     * 错误代码
     */
    private int errorCode;

    /**
     * SQL 执行状态码
     */
    private String sqlState;

    public ServerException (String message, int errorCode, String sqlState) {
        super(message);
        this.errorCode = errorCode;
        this.sqlState = sqlState;
    }

    /**
     * @see ErrorCode
     */
    public int getErrorCode () {
        return errorCode;
    }

    public String getSqlState () {
        return sqlState;
    }
}
