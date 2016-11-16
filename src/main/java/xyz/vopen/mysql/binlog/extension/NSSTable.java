package xyz.vopen.mysql.binlog.extension;

import xyz.vopen.mysql.binlog.DomainSerializable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 表结构实体
 * Created by ive on 31/10/2016.
 */
public class NSSTable extends DomainSerializable {

    private static final long serialVersionUID = 6678524008943553031L;

    /**
     * 类实体对象
     **/
    private final Class<?> clazz;
    /**
     * 数据来源表
     */
    private final String sourceTable;
    /**
     * 数据输出表
     */
    private final String destTable;
    /**
     * 主键集合
     */
    private Key[] keys;
    /**
     * 日期类型数据列下标
     */
    private Map<Integer, String> timestampIndex; // 时间戳字段下标 index
    /**
     * 数据表列集合
     */
    private Map<Integer, String> columns = new ConcurrentHashMap<Integer, String>();

    public static Builder newBuilder () {
        return new Builder();
    }

    private NSSTable (Class<?> clazz, String sourceTable, String destTable) {
        this.clazz = clazz;
        this.sourceTable = sourceTable;
        this.destTable = destTable;
    }

    public static class Builder {
        private Class<?> clazz;
        private String sourceTable;
        private String destTable;

        public Builder clazz (Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder sourceTable (String sourceTable) {
            this.sourceTable = sourceTable;
            return this;
        }

        public Builder destTable (String destTable) {
            this.destTable = destTable;
            return this;
        }

        public final NSSTable build () {
            return new NSSTable(clazz, sourceTable, destTable);
        }
    }

    public Class<?> getClazz () {
        return clazz;
    }

    public String getSourceTable () {
        return sourceTable;
    }

    public String getDestTable () {
        return destTable;
    }

    public Key[] getKeys () {
        return keys;
    }

    public void setKeys (Key[] keys) {
        this.keys = keys;
    }

    public Map<Integer, String> getTimestampIndex () {
        return timestampIndex;
    }

    public void setTimestampIndex (Map<Integer, String> timestampIndex) {
        this.timestampIndex = timestampIndex;
    }

    public Map<Integer, String> getColumns () {
        return columns;
    }

    public void setColumns (Map<Integer, String> columns) {
        this.columns = columns;
    }
}
