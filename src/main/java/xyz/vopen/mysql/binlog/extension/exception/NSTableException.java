package xyz.vopen.mysql.binlog.extension.exception;

/**
 * @NSTable 配置错误
 */
public class NSTableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NSTableException () {
    }

    public NSTableException (String message) {
        super(message);
    }

    public NSTableException (String message, Throwable cause) {
        super(message, cause);
    }

    public NSTableException (Throwable cause) {
        super(cause);
    }

    public NSTableException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
