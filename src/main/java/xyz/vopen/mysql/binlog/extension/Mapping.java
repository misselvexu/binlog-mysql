package xyz.vopen.mysql.binlog.extension;

import xyz.vopen.mysql.binlog.event.deserialization.ColumnType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@link ColumnType#TINY}: Integer
 * {@link ColumnType#SHORT}: Integer
 * {@link ColumnType#LONG}: Integer
 * {@link ColumnType#INT24}: Integer
 * {@link ColumnType#YEAR}: Integer
 * {@link ColumnType#ENUM}: Integer
 * {@link ColumnType#SET}: Long
 * {@link ColumnType#LONGLONG}: Long
 * {@link ColumnType#FLOAT}: Float
 * {@link ColumnType#DOUBLE}: Double
 * {@link ColumnType#BIT}: java.util.BitSet
 * {@link ColumnType#DATETIME}: Long
 * {@link ColumnType#DATETIME_V2}: Long
 * {@link ColumnType#NEWDECIMAL}: java.math.BigDecimal
 * {@link ColumnType#TIMESTAMP}: Long
 * {@link ColumnType#TIMESTAMP_V2}: Long
 * {@link ColumnType#DATE}: Long
 * {@link ColumnType#TIME}: Long
 * {@link ColumnType#TIME_V2}: Long
 * {@link ColumnType#VARCHAR}: byte[]
 * {@link ColumnType#VAR_STRING}: byte[]
 * {@link ColumnType#STRING}: byte[]
 * {@link ColumnType#BLOB}: byte[]
 * {@link ColumnType#GEOMETRY}: byte[]
 * Created by ive on 31/10/2016.
 */
public abstract class Mapping {

    private String database;

    /**
     * 检查数据库是否一致
     *
     * @param database 数据库
     * @return return true if same ,otherwise false
     */
    public boolean belong (String database) {
        return database != null && database.length() > 0 && database.equals(this.database);
    }

    /**
     * 格式化日期
     *
     * @param format    日期格式
     * @param timestamp 时间戳
     * @return 格式化日期
     */
    protected String getDate (String format, Long timestamp) {
        Date date = new Date(timestamp);
        return new SimpleDateFormat(format).format(date);
    }

    public String getDatabase () {
        return database;
    }

    public void setDatabase (String database) {
        this.database = database;
    }
}
