package in.brewcode.api.exception;

public class UserNotFoundException extends ResourceNotFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8104623302947835219L;

	public UserNotFoundException() {
		super();
		
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public UserNotFoundException(String message) {
		super(message);
		
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
		
	}

}
