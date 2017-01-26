package in.brewcode.api.web;

import in.brewcode.api.auth.server.service.CustomUserDetailsService;
import in.brewcode.api.dto.AuthorRegistrationDto;
import in.brewcode.api.exception.UserAlreadyExistsException;
import in.brewcode.api.web.common.BaseController;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Preconditions;

@Controller
@RequestMapping("/user")
public class UserRegistrationController extends BaseController{
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@RequestMapping("/register")
	@ResponseStatus(value=HttpStatus.CREATED)
	public void registerUser (@RequestBody @Valid AuthorRegistrationDto authorRegistrationDto) throws UserAlreadyExistsException{
		
	Preconditions.checkNotNull(authorRegistrationDto);
	
		customUserDetailsService.registerUser(authorRegistrationDto);
		
	}

	
}
