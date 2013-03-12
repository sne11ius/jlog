package nu.wasis.jlog.exception;

public class NotAllowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotAllowedException() {
		super();
	}

	public NotAllowedException(String arg0) {
		super(arg0);
	}

	public NotAllowedException(Throwable arg0) {
		super(arg0);
	}

	public NotAllowedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotAllowedException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
