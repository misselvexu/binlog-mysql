package xyz.vopen.mysql.binlog.extension;

import xyz.vopen.mysql.binlog.extension.exception.NSTableMappingException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 表映射
 * Created by ive on 31/10/2016.
 */
public class TableMapping extends Mapping {

    // 类实体对象
    private final Class<?> clazz;
    // 表
    private final String sourceTable;
    private final String destTable;
    // 表ID
    private final Long tableId;
    private final Key[] keys;
    private final Map<Integer, String> timestampIndex; // 时间戳字段下标 index
    private final Map<Integer, String> columns;

    public static Builder newBuilder () {
        return new Builder();
    }

    private TableMapping (Class<?> clazz, String sourceTable, String destTable, Long tableId, Key[] keys, Map<Integer, String> columns, Map<Integer, String> timestampIndex) {
        this.clazz = clazz;
        this.sourceTable = sourceTable;
        this.destTable = destTable;
        this.tableId = tableId;
        this.keys = keys;
        this.columns = columns;
        this.timestampIndex = timestampIndex;
    }

    public static class Builder {
        // 类实体对象
        private Class<?> clazz;
        // 表
        private String sourceTable;
        private String destTable;
        // 表ID
        private Long tableId;
        private Key[] keys;
        private Map<Integer, String> columns;
        private Map<Integer, String> timestampIndex;

        public Builder nssTable (NSSTable table) {
            this.clazz = table.getClass();
            this.sourceTable = table.getSourceTable();
            this.destTable = table.getDestTable();
            this.columns = table.getColumns();
            this.keys = table.getKeys();
            this.timestampIndex = table.getTimestampIndex();
            return this;
        }

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

        public Builder tableId (Long tableId) {
            this.tableId = tableId;
            return this;
        }

        public Builder columns (Map<Integer, String> columns) {
            this.columns = columns;
            return this;
        }

        public Builder keys (Key[] keys) {
            this.keys = keys;
            return this;
        }

        public Builder timestampIndex (Map<Integer, String> timestampIndex) {
            this.timestampIndex = timestampIndex;
            return this;
        }

        public final TableMapping build () {
            return new TableMapping(clazz, sourceTable, destTable, tableId, keys, columns, timestampIndex);
        }
    }

    /**
     * 获取字段名称
     *
     * @param index 下标
     * @return 字段名称
     */
    public String getColumnName (Integer index) {
        return columns.get(index);
    }

    public String insertSql () {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        if (columns != null && columns.size() > 0) {
            int columnSize = columns.size();
            sql.append(destTable);
            sql.append(" ( ");
            int index = 0;
            StringBuilder questionMark = new StringBuilder();
            for (Map.Entry<Integer, String> entry : columns.entrySet()) {
                if (index++ == columnSize - 1) {
                    sql.append(entry.getValue()).append(" ) ");
                    questionMark.append("?");
                } else {
                    sql.append(entry.getValue()).append(",");
                    questionMark.append("?").append(",");
                }
            }
            sql.append(" VALUES (");
            sql.append(questionMark);
            sql.append(")");
        }
        return sql.toString();
    }

    public String updateSql () {
        String sql = "UPDATE #TABLE# SET #COLUMNS# WHERE #WHERES#";
        StringBuilder _columns = new StringBuilder("");
        if (columns != null && columns.size() > 0) {

            // 去除主键的更新set
            int columnSize = columns.size() - keys.length;
            int index = 0;
            for (Map.Entry<Integer, String> entry : columns.entrySet()) {
                if (!isKey(entry.getValue())) {
                    if (index++ == columnSize - 1) {
                        _columns.append(entry.getValue()).append("=?");
                    } else {
                        _columns.append(entry.getValue()).append("=?").append(",");
                    }
                }
            }
        }

        if (keys == null || keys.length == 0) {
            throw new NSTableMappingException("错误:表的更新语句,必须要设置主键");
        }

        StringBuilder wheres = new StringBuilder("");
        int index = 0;
        int keySize = keys.length;
        for (Key key : keys) {
            if (index++ == keySize - 1) {
                wheres.append(key.getKey()).append("=?");
            } else {
                wheres.append(key.getKey()).append("=?").append(" AND ");
            }
        }

        sql = sql.replaceAll("#TABLE#", destTable).replaceAll("#COLUMNS#", _columns.toString()).replaceAll("#WHERES#", wheres.toString());

        return sql;
    }

    public String deleteSql () {
        String sql = "DELETE FROM #TABLE# WHERE #WHERES#";
        StringBuilder wheres = new StringBuilder("");
        int index = 0;
        int keySize = keys.length;
        for (Key key : keys) {
            if (index++ == keySize - 1) {
                wheres.append(key.getKey()).append("=?");
            } else {
                wheres.append(key.getKey()).append("=?").append(" AND ");
            }
        }
        sql = sql.replaceAll("#TABLE#", destTable).replaceAll("#WHERES#", wheres.toString());
        return sql;
    }


    public List<Object> getInsertParams (Serializable[] serializables) {
        if (keys == null || keys.length == 0) {
            throw new NSTableMappingException("错误:表的更新语句,必须要设置主键");
        }
        List<Object> params = new ArrayList<>();

        if (serializables != null && serializables.length > 0) {
            int index = 0;
            for (Serializable serializable : serializables) {

                // 判断日期类型
                int currentIndex = index++;
                if (isDate(currentIndex)) {
                    params.add(getDate(timestampIndex.get(currentIndex), Long.valueOf(((Object)serializables[currentIndex]).toString())));
                    continue;
                }

                // 判断字符串类型
                if (serializable instanceof byte[]) {
                    params.add(new String((byte[]) serializable));
                } else {
                    params.add(serializable);
                }

            }
        }

        return params;
    }


    /**
     * 获取 参数
     *
     * @param serializables 数据源
     */
    public List<Object> getUpdateParams (Serializable[] serializables) {
        if (keys == null || keys.length == 0) {
            throw new NSTableMappingException("错误:表的更新语句,必须要设置主键");
        }
        List<Object> params = new ArrayList<>();

//        System.out.println("主键:" + Arrays.toString(keys));
//        System.out.println("日期:" + Arrays.toString(timestampIndex.keySet().toArray()));

        if (serializables != null && serializables.length > 0) {
            int index = 0;
            for (Serializable serializable : serializables) {

//                System.out.println(serializable.getClass());

                int currentIndex = index++;

                // 去除 主键的更新
                if (isKey(currentIndex)) {
                    continue;
                }

                // 判断日期类型
                if (isDate(currentIndex)) {
                    params.add(getDate(timestampIndex.get(currentIndex), Long.valueOf(((Object)serializables[currentIndex]).toString())));
                    continue;
                }

                // 判断字符串类型
                if (serializable instanceof byte[]) {
                    params.add(new String((byte[]) serializable));
                } else {
                    params.add(serializable);
                }

            }
        }

        System.out.println("更新的参数:" + Arrays.toString(params.toArray()));

        params.addAll(getWhereParams(serializables));

        System.out.println("条件的参数:" + Arrays.toString(params.toArray()));
        return params;
    }

    public List<Object> getWhereParams (Serializable[] serializables) {
        if (keys == null || keys.length == 0) {
            throw new NSTableMappingException("错误:表的更新语句,必须要设置主键");
        }

        List<Object> params = new ArrayList<>();

        if (serializables != null && serializables.length > 0) {
            for (Key key : keys) {

                // 判断日期类型
                if (isDate(key.getIndex())) {
                    params.add(getDate(timestampIndex.get(key.getIndex()), Long.valueOf(((Object)serializables[key.getIndex()]).toString())));
                    continue;
                }

                // 判断字符串类型
                if (serializables[key.getIndex()] instanceof byte[]) {
                    params.add(new String((byte[]) serializables[key.getIndex()]));
                } else {
                    params.add(serializables[key.getIndex()]);
                }
            }
        }
        return params;
    }

    private boolean isDate (int index) {
        if (timestampIndex != null) {
            if (timestampIndex.containsKey(index)) {
                return true
                        ;
            }
        }
        return false;
    }

    private boolean isKey (int index) {
        if (keys != null && keys.length > 0) {
            for (Key key : keys) {
                if (key.getIndex() == index) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isKey (String column) {
        if (keys != null && keys.length > 0) {
            for (Key key : keys) {
                boolean sim = key.getKey().equalsIgnoreCase(column);
                if (sim) return true;
            }
        }
        return false;
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

    public Long getTableId () {
        return tableId;
    }

    public Key[] getKeys () {
        return keys;
    }

    public Map<Integer, String> getTimestampIndex () {
        return timestampIndex;
    }

    public Map<Integer, String> getColumns () {
        return columns;
    }
}
