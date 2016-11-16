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
package xyz.vopen.mysql.binlog.event;

import xyz.vopen.mysql.binlog.DomainSerializable;

/**
 * 字节数组类型数据
 *
 * @author <a href="mailto:cinc.jan@gmail.com">CiNC</a>
 */
public class ByteArrayEventData extends DomainSerializable implements EventData {

    private static final long serialVersionUID = -4291816792231991410L;
    private byte[] data;

    public byte[] getData () {
        return data;
    }

    public void setData (byte[] data) {
        this.data = data;
    }

    @Override
    public String toString () {
//        final StringBuilder sb = new StringBuilder();
//        sb.append("ByteArrayEventData");
//        sb.append("{dataLength=").append(data.length);
//        sb.append('}');
//        return sb.toString();

        return toJson(true);
    }
}
