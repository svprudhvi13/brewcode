package in.brewcode.api.exception;

public class ResourceNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6048469433099990804L;

	public ResourceNotFoundException() {
		super();
		}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
			}

	public ResourceNotFoundException(String message) {
		super(message);
		}

	public ResourceNotFoundException(Throwable cause) {
		super(cause);
			}

}
