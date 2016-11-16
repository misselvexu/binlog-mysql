package xyz.vopen.mysql.binlog.extension.annotation;

import java.lang.annotation.*;

/**
 * 字段注解:主键标识
 * Created by ive on 31/10/2016.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NSKey {

    String name () default "";

    String value () default "";

}
