package in.brewcode.api.auth.server.service;

import in.brewcode.api.dto.AuthorRegistrationDto;
import in.brewcode.api.exception.UserAlreadyExistsException;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService{

	public void registerUser(AuthorRegistrationDto authorRegistrationDto) throws UserAlreadyExistsException;
	public void updateUser(AuthorRegistrationDto authorRegistrationDto);
	public void resetPassword(String userNameOrEmail, String password);
}
