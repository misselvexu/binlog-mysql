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
public class QueryEventData extends DomainSerializable implements EventData {

    private static final long serialVersionUID = 3802900470726467278L;
    /**
     * 线程 ID
     */
    private long threadId;

    /**
     * 执行时间
     */
    private long executionTime;

    /**
     * 错误代码
     */
    private int errorCode;

    /**
     * 所属数据库
     */
    private String database;

    /**
     * sql 代码
     */
    private String sql;

    public long getThreadId () {
        return threadId;
    }

    public void setThreadId (long threadId) {
        this.threadId = threadId;
    }

    public long getExecutionTime () {
        return executionTime;
    }

    public void setExecutionTime (long executionTime) {
        this.executionTime = executionTime;
    }

    public int getErrorCode () {
        return errorCode;
    }

    public void setErrorCode (int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDatabase () {
        return database;
    }

    public void setDatabase (String database) {
        this.database = database;
    }

    public String getSql () {
        return sql;
    }

    public void setSql (String sql) {
        this.sql = sql;
    }

    @Override
    public String toString () {
//        final StringBuilder sb = new StringBuilder();
//        sb.append("QueryEventData");
//        sb.append("{threadId=").append(threadId);
//        sb.append(", executionTime=").append(executionTime);
//        sb.append(", errorCode=").append(errorCode);
//        sb.append(", database='").append(database).append('\'');
//        sb.append(", sql='").append(sql).append('\'');
//        sb.append('}');
//        return sb.toString();
        return toJson(true);
    }
}
