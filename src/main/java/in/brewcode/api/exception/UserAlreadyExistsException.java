package in.brewcode.api.exception;

public final class UserAlreadyExistsException extends
		ResourceAlreadyExistsException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8082160795671382810L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}

	public UserAlreadyExistsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
