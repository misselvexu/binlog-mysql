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
import xyz.vopen.mysql.binlog.event.deserialization.ChecksumType;

/**
 * FormatDescription事件数据
 *
 * @author <a href="mailto:cinc.jan@gmail.com">CiNC</a>
 */
public class FormatDescriptionEventData extends DomainSerializable implements EventData {

    private static final long serialVersionUID = -5617717410875808608L;
    private int binlogVersion;
    private String serverVersion;
    private int headerLength;
    private ChecksumType checksumType;

    public int getBinlogVersion () {
        return binlogVersion;
    }

    public void setBinlogVersion (int binlogVersion) {
        this.binlogVersion = binlogVersion;
    }

    public String getServerVersion () {
        return serverVersion;
    }

    public void setServerVersion (String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public int getHeaderLength () {
        return headerLength;
    }

    public void setHeaderLength (int headerLength) {
        this.headerLength = headerLength;
    }

    public ChecksumType getChecksumType () {
        return checksumType;
    }

    public void setChecksumType (ChecksumType checksumType) {
        this.checksumType = checksumType;
    }

    @Override
    public String toString () {
//        final StringBuilder sb = new StringBuilder();
//        sb.append("FormatDescriptionEventData");
//        sb.append("{binlogVersion=").append(binlogVersion);
//        sb.append(", serverVersion='").append(serverVersion).append('\'');
//        sb.append(", headerLength=").append(headerLength);
//        sb.append(", checksumType=").append(checksumType);
//        sb.append('}');
//        return sb.toString();
        return toJson(true);
    }
}
