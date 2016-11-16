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
package xyz.vopen.mysql.binlog.network.protocol;

import xyz.vopen.mysql.binlog.nio.ByteArrayInputStream;

import java.io.IOException;

/**
 * @author <a href="mailto:cinc.jan@gmail.com">CiNC</a>
 */
public class ErrorPacket implements Packet {

    public static final byte HEADER = (byte) 0xFF;

    private int errorCode;
    private String sqlState;
    private String errorMessage;

    public ErrorPacket (byte[] bytes) throws IOException {
        this(bytes, 0);
    }

    @SuppressWarnings("resource")
    public ErrorPacket (byte[] bytes, int offset) throws IOException {
        ByteArrayInputStream buffer = new ByteArrayInputStream(bytes);
        buffer.skip(offset);
        this.errorCode = buffer.readInteger(2);
        if (buffer.peek() == '#') {
            buffer.skip(1); // marker of the SQL State
            this.sqlState = buffer.readString(5);
        }
        this.errorMessage = buffer.readString(buffer.available());
    }

    public int getErrorCode () {
        return errorCode;
    }

    public String getSqlState () {
        return sqlState;
    }

    public String getErrorMessage () {
        return errorMessage;
    }
}
