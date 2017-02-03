package in.brewcode.api.exception;

public class InvalidAccessException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2213105370670294284L;

	public InvalidAccessException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

	public InvalidAccessException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public InvalidAccessException(String message) {
		super(message);
	
	}

	public InvalidAccessException(Throwable cause) {
		super(cause);
	
	}

}
