package in.brewcode.api.exception;

public class EntityNotFoundException extends ResourceNotFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2780031391876122732L;

	public EntityNotFoundException() {
		super();
	}

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public EntityNotFoundException(String message) {
		super(message);
		
	}

	public EntityNotFoundException(Throwable cause) {
		super(cause);
		
	}

}
