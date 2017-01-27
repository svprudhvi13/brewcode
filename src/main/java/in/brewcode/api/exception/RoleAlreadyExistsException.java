package in.brewcode.api.exception;

public class RoleAlreadyExistsException extends ResourceAlreadyExistsException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6983737398513751064L;

	public RoleAlreadyExistsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RoleAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoleAlreadyExistsException(String message) {
		super(message);
	}

	public RoleAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
