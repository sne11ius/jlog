package nu.wasis.jlog.exception;

/**
 * Thrown if someone is not allowed to do something.
 */
public class NotAllowedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotAllowedException() {
        super();
    }

    public NotAllowedException(final String arg0) {
        super(arg0);
    }

    public NotAllowedException(final Throwable arg0) {
        super(arg0);
    }

    public NotAllowedException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }

    public NotAllowedException(final String arg0, final Throwable arg1, final boolean arg2, final boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}
