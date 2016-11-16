package xyz.vopen.mysql.binlog.extension.annotation;

import java.lang.annotation.*;

/**
 * 字段注解:表明实体和数据库对应关系
 * Created by ive on 31/10/2016.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NSColumn {

    /**
     * 默认是字段名称
     *
     * @see #name()
     */
    String value () default "";

    /**
     * 字段名称(不填默认与字段名称相同)
     */
    String name () default "";

    /**
     * 数据库字段在表中的下表顺序(不填与字段在类中的什么顺序一致)
     */
    int index () default -1;

    /**
     * 是否忽略改字段
     */
    boolean ignore () default false;

    /**
     * 是否是时间类型
     */
    boolean timestamp () default false;

    /**
     * 日期格式化
     */
    String format () default "yyyy-MM-dd HH:mm:ss";

}
