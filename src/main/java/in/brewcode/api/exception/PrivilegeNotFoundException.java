package in.brewcode.api.exception;

public class PrivilegeNotFoundException extends ResourceNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8233046014004125603L;

	public PrivilegeNotFoundException() {
		super();
	}

	public PrivilegeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrivilegeNotFoundException(String message) {
		super(message);
	}

	public PrivilegeNotFoundException(Throwable cause) {
		super(cause);
	}

}
