package nu.wasis.jlog.exception;

/**
 * Wird verwendet, wenn ungueltige Daten geposted werden.
 */
public class IllegalDataException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IllegalDataException() {
    }

    public IllegalDataException(final String arg0) {
        super(arg0);
    }

    public IllegalDataException(final Throwable arg0) {
        super(arg0);
    }

    public IllegalDataException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }

    public IllegalDataException(final String arg0, final Throwable arg1, final boolean arg2, final boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}
