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

import java.io.Serializable;

/**
 * 事件抽象基类
 *
 * @author <a href="mailto:cinc.jan@gmail.com">CiNC</a>
 */
public class Event extends DomainSerializable implements Serializable {

    private static final long serialVersionUID = -2384286979550390947L;
    private EventHeader header;
    private EventData data;

    public Event (EventHeader header, EventData data) {
        this.header = header;
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public <T extends EventHeader> T getHeader () {
        return (T) header;
    }

    @SuppressWarnings("unchecked")
    public <T extends EventData> T getData () {
        return (T) data;
    }

    @Override
    public String toString () {
//        final StringBuilder sb = new StringBuilder();
//        sb.append("Event");
//        sb.append("{header=").append(header);
//        sb.append(", data=").append(data);
//        sb.append('}');
//        return sb.toString();
        return toJson(true);
    }
}
