package xyz.vopen.mysql.binlog.extension.exception;

/**
 * 表映射错误
 */
public class NSTableMappingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NSTableMappingException () {
    }

    public NSTableMappingException (String message) {
        super(message);
    }

    public NSTableMappingException (String message, Throwable cause) {
        super(message, cause);
    }

    public NSTableMappingException (Throwable cause) {
        super(cause);
    }

    public NSTableMappingException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
