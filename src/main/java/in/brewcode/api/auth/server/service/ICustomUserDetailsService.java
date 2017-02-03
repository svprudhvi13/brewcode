package in.brewcode.api.auth.server.service;

import in.brewcode.api.dto.AuthorRegistrationDto;
import in.brewcode.api.exception.UserAlreadyExistsException;
import in.brewcode.api.exception.UserNotFoundException;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface ICustomUserDetailsService extends UserDetailsService{

	public AuthorRegistrationDto getUserProfile(String username) throws UserNotFoundException;
	public void registerUser(AuthorRegistrationDto authorRegistrationDto) throws UserAlreadyExistsException;
	public void updateUser(AuthorRegistrationDto authorRegistrationDto);
	public void resetPassword(String userNameOrEmail, String password);
}
