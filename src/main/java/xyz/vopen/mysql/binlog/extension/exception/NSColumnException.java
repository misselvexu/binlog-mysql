package xyz.vopen.mysql.binlog.extension.exception;

/**
 * @NSColumn 配置错误
 */
public class NSColumnException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NSColumnException () {
    }

    public NSColumnException (String message) {
        super(message);
    }

    public NSColumnException (String message, Throwable cause) {
        super(message, cause);
    }

    public NSColumnException (Throwable cause) {
        super(cause);
    }

    public NSColumnException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
