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

import java.util.BitSet;

/**
 * @author <a href="mailto:cinc.jan@gmail.com">CiNC</a>
 */
public class TableMapEventData extends DomainSerializable implements EventData {

    private static final long serialVersionUID = 2604959418318349407L;

    /**
     * 实例表唯一 ID
     */
    private long tableId;

    /**
     * 数据库
     */
    private String database;

    /**
     * 表名称
     */
    private String table;

    /**
     * 列类型数组
     */
    private byte[] columnTypes;

    /**
     * 列头信息
     */
    private int[] columnMetadata;

    /**
     * 列空标记
     */
    private BitSet columnNullability;

    public long getTableId () {
        return tableId;
    }

    public void setTableId (long tableId) {
        this.tableId = tableId;
    }

    public String getDatabase () {
        return database;
    }

    public void setDatabase (String database) {
        this.database = database;
    }

    public String getTable () {
        return table;
    }

    public void setTable (String table) {
        this.table = table;
    }

    public byte[] getColumnTypes () {
        return columnTypes;
    }

    public void setColumnTypes (byte[] columnTypes) {
        this.columnTypes = columnTypes;
    }

    public int[] getColumnMetadata () {
        return columnMetadata;
    }

    public void setColumnMetadata (int[] columnMetadata) {
        this.columnMetadata = columnMetadata;
    }

    public BitSet getColumnNullability () {
        return columnNullability;
    }

    public void setColumnNullability (BitSet columnNullability) {
        this.columnNullability = columnNullability;
    }

    @Override
    public String toString () {
//        final StringBuilder sb = new StringBuilder();
//        sb.append("TableMapEventData");
//        sb.append("{tableId=").append(tableId);
//        sb.append(", database='").append(database).append('\'');
//        sb.append(", table='").append(table).append('\'');
//        sb.append(", columnTypes=").append(columnTypes == null ? "null" : "");
//        for (int i = 0; columnTypes != null && i < columnTypes.length; ++i) {
//            sb.append(i == 0 ? "" : ", ").append(columnTypes[i]);
//        }
//        sb.append(", columnMetadata=").append(columnMetadata == null ? "null" : "");
//        for (int i = 0; columnMetadata != null && i < columnMetadata.length; ++i) {
//            sb.append(i == 0 ? "" : ", ").append(columnMetadata[i]);
//        }
//        sb.append(", columnNullability=").append(columnNullability);
//        sb.append('}');
//        return sb.toString();
        return toJson(true);
    }
}
