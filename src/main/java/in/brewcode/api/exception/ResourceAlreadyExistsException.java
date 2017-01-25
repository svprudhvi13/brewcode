package in.brewcode.api.exception;

public class ResourceAlreadyExistsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2388853678234003368L;

	public ResourceAlreadyExistsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
			}

	public ResourceAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ResourceAlreadyExistsException(String message) {
		super(message);
		
	}

	public ResourceAlreadyExistsException(Throwable cause) {
		super(cause);
		
	}

	
}
