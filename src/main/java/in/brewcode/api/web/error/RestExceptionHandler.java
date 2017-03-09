package in.brewcode.api.web.error;

import in.brewcode.api.exception.InvalidAccessException;
import in.brewcode.api.exception.PrivilegeNotFoundException;
import in.brewcode.api.exception.UserNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
		return handleExceptionInternal(ex, ex.getMessage()+" Invalid request parameters or request body.", new HttpHeaders(), HttpStatus.BAD_REQUEST, request );
		 
	}
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request){
		return handleExceptionInternal(ex,"user not found." , new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	@ExceptionHandler(InvalidAccessException.class)
	public ResponseEntity<Object> handleIllegalUserAccessException(InvalidAccessException ex, WebRequest request){
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}
	
	@ExceptionHandler(PrivilegeNotFoundException.class)
	public ResponseEntity<Object> handlePrivilegeNotFoundException(PrivilegeNotFoundException ex, WebRequest request){
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY, request);
	}
	
	/*@ExceptionHandler(SQLException.class)
	public ResponseEntity<Object> handlesSQLException(Exception ex, WebRequest request){
		return handleExceptionInternal(ex, "db is busy. we are working on it", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}*/
}
