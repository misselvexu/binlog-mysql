package xyz.vopen.mysql.binlog.extension;

import xyz.vopen.mysql.binlog.extension.annotation.NSColumn;
import xyz.vopen.mysql.binlog.extension.annotation.NSKey;
import xyz.vopen.mysql.binlog.extension.annotation.NSTable;
import xyz.vopen.mysql.binlog.extension.exception.NSColumnException;
import xyz.vopen.mysql.binlog.extension.exception.NSTableException;
import xyz.vopen.mysql.binlog.extension.exception.NSTableMappingException;
import xyz.vopen.mysql.binlog.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 实体与日志对应的Table Map解析表
 * Created by ive on 31/10/2016.
 * @author Elve.xu
 */
public final class BinaryLogAnnotationParser {

    private static Logger logger = LoggerFactory.getLogger(BinaryLogAnnotationParser.class);

    /**
     * 扫描初始化
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, NSSTable> scanAndInit (String packageName) throws NSColumnException, NSTableException, NSTableMappingException {
        Map<String, NSSTable> result = new HashMap<>();
        if (packageName != null && packageName.length() > 0) {
            Set<Class<?>> classSet = ClassUtils.getClasses(packageName);
            if (classSet != null) {
                for (Class clazz : classSet) {
                    NSSTablePair pair = parse(clazz);
                    if (pair != null) {
                        String tableName = pair.getTableName();
                        if (tableName != null && tableName.length() > 0) {
                            if (result.containsKey(tableName)) {
                                logger.warn("\t 警告: 表名:{}已经存在 ,Classes : [{} | {}] ", tableName, clazz, result.get(tableName).getClazz());
                            }
                            //
                            result.put(pair.getTableName(), pair.getTable());
                        }
                    }
                }

                // check error
                for (Map.Entry<String, NSSTable> entry : result.entrySet()) {
                    String key = entry.getKey();
                    NSSTable table = entry.getValue();
                    if (table == null) {
                        throw new NSTableMappingException("异常:表名映射异常Name= " + key);
                    }
                    int size = table.getColumns().size();
                    int lastKey = getMapLastItem(table.getColumns());
                    if (lastKey != (size - 1)) {
                        throw new NSTableMappingException("异常:表字段下标排序不连续异常 ,Table Name = " + key + " ,Class = " + table.getClazz());
                    }
                }
            }
        }

        return result;
    }

    public static class NSSTablePair {
        private String tableName;
        private NSSTable table;

        public NSSTablePair (String tableName, NSSTable table) {
            this.tableName = tableName;
            this.table = table;
        }

        public String getTableName () {
            return tableName;
        }

        public void setTableName (String tableName) {
            this.tableName = tableName;
        }

        public NSSTable getTable () {
            return table;
        }

        public void setTable (NSSTable table) {
            this.table = table;
        }
    }

    public static NSSTablePair parse (Class<?> clazz) {

        NSSTable.Builder builder = NSSTable.newBuilder();
        builder.clazz(clazz);
        String tableName = null;
        NSTable table = clazz.getAnnotation(NSTable.class);
        if (table != null) {
            tableName = table.source();

            if ((tableName == null || tableName.trim().length() == 0) || (table.dest() == null || table.dest().trim().length() == 0)) {
                throw new NSTableException("错误 : Class: " + clazz + "必须设置@NSTable(source = 'source_table_name' ,dest = 'dest_table_name')");
            }

            builder.sourceTable(tableName).destTable(table.dest());
        } else {
            // 没有@NSTable 的标记类,忽略
            return null;
        }

        NSSTable nssTable = builder.build();

        Map<Integer, String> temp = new HashMap<>();
        List<Key> keys = new ArrayList<>();// 存储主键
        Map<Integer, String> timestampIndex = new HashMap<>();

        Field[] fields = clazz.getDeclaredFields();
        int index = 0;
        for (Field field : fields) {
            // 复杂类型过滤
            boolean support = Types.support(field.getType());
            if (!support) {
                continue;
            }

            if(field.getName().equalsIgnoreCase("serialVersionUID")) {
                continue;
            }

            int currentIndex = index++;
            String columnName = field.getName();
            int columnIndex = -1;

            boolean timestamp = false; // 是否为日期
            String format = ""; // 格式化日期
            // check @NSColumn
            NSColumn column = field.getAnnotation(NSColumn.class);
            if (column != null) {
                if (column.ignore()) {
                    index = currentIndex;
                    continue;
                }
                if (column.name() != null && column.name().trim().length() > 0) {
                    columnName = column.name();
                } else {
                    if (column.value() != null && column.value().trim().length() > 0) {
                        columnName = column.value();
                    }
                }
                if (column.index() != -1) {
                    columnIndex = column.index();
                }

                // 是时间戳类型
                timestamp = column.timestamp();
                format = column.format();

            }

            // if @NSColumn is not exist , set column sort = properties declare sort.
            if (columnIndex == -1) {
                columnIndex = currentIndex;
            }

            // check timestamp properties
            if (timestamp) {
                timestampIndex.put(columnIndex, format);
            }

            if (temp.containsKey(columnIndex)) {
                throw new NSColumnException("错误: 类:" + clazz + " , @NSColumn[index=" + columnIndex + " ,name=" + columnName + "]重复.");
            }
            temp.put(columnIndex, columnName);

            // check @NSKey
            NSKey key = field.getAnnotation(NSKey.class);
            if (key != null) {
                String keyName = key.name();
                if (keyName == null || keyName.length() == 0) {
                    keyName = key.value();
                    if (keyName == null || keyName.length() == 0) {
                        keyName = field.getName();
                    }
                }

                keys.add(new Key(columnIndex, keyName));
            }
        }

        // check key
        if (keys.size() < 1) {
            throw new NSColumnException("错误: 类: " + clazz + " , 必须要有主键,必须设置 @NSKey");
        }

        // columns sort
        Set<Integer> integers = temp.keySet();
        Integer[] indexsArray = integers.toArray(new Integer[integers.size()]);
        Arrays.sort(indexsArray);
        Map<Integer, String> columns = new HashMap<>();
        for (Integer _index : indexsArray) {
            columns.put(_index, temp.get(_index));
        }

        nssTable.setColumns(columns);
        Key[] keyArray = keys.toArray(new Key[keys.size()]);
        Arrays.sort(keyArray);
        nssTable.setKeys(keyArray);

        nssTable.setTimestampIndex(timestampIndex);

        return new NSSTablePair(tableName, nssTable);
    }


    private static <K, V> K getMapLastItem (Map<K, V> map) {
        if (map != null) {
            Iterator<K> it = map.keySet().iterator();
            while (true) {
                K current = it.next();
                if (!it.hasNext()) {
                    return current;
                }
            }
        }
        return null;
    }

}
