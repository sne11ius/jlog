package nu.wasis.jlog.exception;

public class InvalidConfigurationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidConfigurationException(final String message) {
        super(message);
    }

    public InvalidConfigurationException(final Exception e) {
        super(e);
    }

}
