package xyz.vopen.mysql.binlog.extension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 默认实体所支持的数据累成
 * Created by ive on 31/10/2016.
 */
public final class Types {

    private final static List<Class<?>> SUPPORT_CLASSES = new ArrayList<>();

    static {
        // 字符串
        SUPPORT_CLASSES.add(String.class);
        // 基本数据类型
        SUPPORT_CLASSES.add(Integer.class);
        SUPPORT_CLASSES.add(Double.class);
        SUPPORT_CLASSES.add(Long.class);
        SUPPORT_CLASSES.add(Float.class);
        SUPPORT_CLASSES.add(Byte.class);
        SUPPORT_CLASSES.add(Short.class);
        SUPPORT_CLASSES.add(Character.class);
        SUPPORT_CLASSES.add(Boolean.class);
        // 支持时间类型的字段
        SUPPORT_CLASSES.add(Date.class);
    }


    public static boolean support (Class<?> clazz) {
        return SUPPORT_CLASSES.contains(clazz);
    }
}
