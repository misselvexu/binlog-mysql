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
package xyz.vopen.mysql.binlog.network.protocol.command;

import xyz.vopen.mysql.binlog.network.ClientCapabilities;
import xyz.vopen.mysql.binlog.nio.ByteArrayOutputStream;

import java.io.IOException;

/**
 * @author <a href="mailto:cinc.jan@gmail.com">CiNC</a>
 */
public class SSLRequestCommand implements Command {

    private int clientCapabilities;
    private int collation;

    public void setClientCapabilities (int clientCapabilities) {
        this.clientCapabilities = clientCapabilities;
    }

    public void setCollation (int collation) {
        this.collation = collation;
    }

    @SuppressWarnings("resource")
    @Override
    public byte[] toByteArray () throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int clientCapabilities = this.clientCapabilities;
        if (clientCapabilities == 0) {
            clientCapabilities = ClientCapabilities.LONG_FLAG |
                    ClientCapabilities.PROTOCOL_41 | ClientCapabilities.SECURE_CONNECTION;
        }
        clientCapabilities |= ClientCapabilities.SSL;
        buffer.writeInteger(clientCapabilities, 4);
        buffer.writeInteger(0, 4); // maximum packet length
        buffer.writeInteger(collation, 1);
        for (int i = 0; i < 23; i++) {
            buffer.write(0);
        }
        return buffer.toByteArray();
    }

}
