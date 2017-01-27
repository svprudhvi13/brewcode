package in.brewcode.api.exception;

public class RoleNotFoundException extends ResourceNotFoundException {

	public RoleNotFoundException() {
		super();
	}

	public RoleNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public RoleNotFoundException(String message) {
		super(message);
		}

	public RoleNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8535901715062635479L;

}
