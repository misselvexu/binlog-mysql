package xyz.vopen.mysql.binlog.extension.annotation;

import java.lang.annotation.*;

/**
 * Table annotation ;
 * Created by ive on 31/10/2016.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NSTable {

    /**
     * Desc Table Name.
     *
     * @return table name
     */
    String source ();

    String dest ();

}
