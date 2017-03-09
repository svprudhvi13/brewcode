package in.brewcode.api.exception;

public class RoleInUseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072047660013456157L;

	public RoleInUseException() {
		
	}

	public RoleInUseException(String message) {
		super(message);
		
	}

	public RoleInUseException(Throwable cause) {
		super(cause);
		
	}

	public RoleInUseException(String message, Throwable cause) {
		super(message, cause);
		
	}


}
