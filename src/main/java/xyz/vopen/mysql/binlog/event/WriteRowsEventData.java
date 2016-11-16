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
import xyz.vopen.mysql.binlog.event.deserialization.AbstractRowsEventDataDeserializer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

/**
 * 插入数据事件
 *
 * @author <a href="mailto:cinc.jan@gmail.com">CiNC</a>
 */
public class WriteRowsEventData extends DomainSerializable implements EventData {

    private static final long serialVersionUID = 8049455392955316689L;

    /**
     * 表全局 ID
     */
    private long tableId;

    /**
     * 插入的数据列
     */
    private BitSet includedColumns;

    /**
     * 所有的影响行数
     *
     * @see AbstractRowsEventDataDeserializer
     */
    private List<Serializable[]> rows;

    public long getTableId () {
        return tableId;
    }

    public void setTableId (long tableId) {
        this.tableId = tableId;
    }

    public BitSet getIncludedColumns () {
        return includedColumns;
    }

    public void setIncludedColumns (BitSet includedColumns) {
        this.includedColumns = includedColumns;
    }

    public List<Serializable[]> getRows () {
        return rows;
    }

    public void setRows (List<Serializable[]> rows) {
        this.rows = rows;
    }

    @Override
    public String toString () {
        final StringBuilder sb = new StringBuilder();
        sb.append("WriteRowsEventData");
        sb.append("{tableId=").append(tableId);
        sb.append(", includedColumns=").append(includedColumns);
        sb.append(", rows=[");
        for (Serializable[] row : rows) {
            sb.append("\n    ").append(Arrays.toString(parseSerializable(row).toArray())).append(",");
        }
        if (!rows.isEmpty()) {
            sb.replace(sb.length() - 1, sb.length(), "\n");
        }
        sb.append("]}");
        return sb.toString();
//        return toJson(true);
    }
}
