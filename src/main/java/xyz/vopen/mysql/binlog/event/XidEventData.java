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
 * @author <a href="mailto:cinc.jan@gmail.com">CiNC</a>
 */
public class XidEventData extends DomainSerializable implements EventData {

    private static final long serialVersionUID = 6179910753817008958L;
    private long xid;

    public long getXid () {
        return xid;
    }

    public void setXid (long xid) {
        this.xid = xid;
    }

    @Override
    public String toString () {
//        final StringBuilder sb = new StringBuilder();
//        sb.append("XidEventData");
//        sb.append("{xid=").append(xid);
//        sb.append('}');
//        return sb.toString();
        return toJson(true);
    }
}
