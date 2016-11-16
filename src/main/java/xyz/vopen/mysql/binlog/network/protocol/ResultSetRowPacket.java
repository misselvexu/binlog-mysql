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
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:cinc.jan@gmail.com">CiNC</a>
 */
public class ResultSetRowPacket implements Packet {

    private String[] values;

    @SuppressWarnings("resource")
    public ResultSetRowPacket (byte[] bytes) throws IOException {
        ByteArrayInputStream buffer = new ByteArrayInputStream(bytes);
        List<String> values = new LinkedList<String>();
        while (buffer.available() > 0) {
            values.add(buffer.readLengthEncodedString());
        }
        this.values = values.toArray(new String[values.size()]);
    }

    public String[] getValues () {
        return values;
    }

    public String getValue (int index) {
        return values[index];
    }

    public int size () {
        return values.length;
    }

}
