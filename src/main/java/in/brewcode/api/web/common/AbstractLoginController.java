package in.brewcode.api.web.common;

import in.brewcode.api.dto.AuthorDto;

public abstract class AbstractLoginController {
	public abstract boolean validateLogin( AuthorDto authorDto);
//	public abstract boolean isAccessAlive();
	
	
	
}
