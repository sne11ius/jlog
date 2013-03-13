package nu.wasis.jlog.exception;

public class InternalErrorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InternalErrorException() {
        super();
    }

    public InternalErrorException(final String message, final Throwable cause, final boolean enableSuppression,
                                  final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InternalErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InternalErrorException(final String message) {
        super(message);
    }

    public InternalErrorException(final Throwable cause) {
        super(cause);
    }

}
