package nu.wasis.jlog.exception;

public class NoLoginException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoLoginException() {
        super();
    }

    public NoLoginException(final String message, final Throwable cause, final boolean enableSuppression,
                            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NoLoginException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoLoginException(final String message) {
        super(message);
    }

    public NoLoginException(final Throwable cause) {
        super(cause);
    }

}
