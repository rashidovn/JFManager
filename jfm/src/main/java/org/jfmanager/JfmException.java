package org.jfmanager;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 3:38 PM
 */
public class JfmException extends RuntimeException {

    public JfmException() {
    }

    public JfmException(String message) {
        super(message);
    }

    public JfmException(String message, Throwable cause) {
        super(message, cause);
    }

    public JfmException(Throwable cause) {
        super(cause);
    }

    public JfmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
