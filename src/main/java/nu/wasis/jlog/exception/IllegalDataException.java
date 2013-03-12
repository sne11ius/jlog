package nu.wasis.jlog.exception;

public class IllegalDataException extends RuntimeException {

	public IllegalDataException() {
	}

	public IllegalDataException(String arg0) {
		super(arg0);
	}

	public IllegalDataException(Throwable arg0) {
		super(arg0);
	}

	public IllegalDataException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IllegalDataException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
