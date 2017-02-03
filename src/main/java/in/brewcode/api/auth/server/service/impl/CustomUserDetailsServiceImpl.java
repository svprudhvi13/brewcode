package in.brewcode.api.auth.server.service.impl;

import static in.brewcode.api.service.common.ServiceUtils.convertToAuthorEntity;
import static in.brewcode.api.service.common.ServiceUtils.convertToAuthorRegistrationDto;
import static in.brewcode.api.service.common.ServiceUtils.convertToPersonalDetailsEntity;
import in.brewcode.api.auth.server.dao.ICustomUserDao;
import in.brewcode.api.auth.server.service.ICustomUserDetailsService;
import in.brewcode.api.dto.AuthorLoginDto;
import in.brewcode.api.dto.AuthorRegistrationDto;
import in.brewcode.api.exception.UserAlreadyExistsException;
import in.brewcode.api.exception.UserNotFoundException;
import in.brewcode.api.persistence.dao.IPersonalDetailsDao;
import in.brewcode.api.persistence.entity.Author;
import in.brewcode.api.persistence.entity.PersonalDetails;
import in.brewcode.api.persistence.entity.Privilege;
import in.brewcode.api.persistence.entity.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@Service(value = "userDetailsService")
@Transactional
public class CustomUserDetailsServiceImpl implements ICustomUserDetailsService {

	@Autowired
	ICustomUserDao customUserDao;

	@Autowired
	IPersonalDetailsDao personalDetailsDao;

	@Autowired
	PasswordEncoder passwordEncoder;
	//@PreAuthorize("hasROLE='USER'")
	public AuthorRegistrationDto getUserProfile(String username) throws UserNotFoundException{
		
		Preconditions.checkArgument(username!=null||username!="");
		AuthorRegistrationDto authorRegistrationDto = null;
		if(checkIfUserNameAlreadyExists(username)){
			Author author = customUserDao.findByAuthorUserName(username);
			//AuthorLoginDto authorLoginDto = convertToAuthorLoginDto(author);
			PersonalDetails personalDetails = personalDetailsDao.findByAuthor(author);
			//Hibernate.initialize(personalDetails); //No need for initializing fetch type changed to EAGER
			authorRegistrationDto= convertToAuthorRegistrationDto(personalDetails);
			
			//authorRegistrationDto.setAuthorLoginDto(authorLoginDto);

			return authorRegistrationDto;	
		}
		else{
			throw new UserNotFoundException("Can't retreive user profile. Invalid username");
		}
	}
	
	/**
	 * Custom method used for getting token and authorizing user by OAuth2
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		Author author = customUserDao.findByAuthorUserName(username);
		// initializing Collection<GrantedAuthority> as null can't be passed
		// into User constructor
		List<GrantedAuthority> listRoles = new ArrayList<GrantedAuthority>();

		if (author != null) {
			Role role = author.getRole();
			if (role != null) {
				Set<Privilege> privileges = author.getRole()
						.getRolePrivileges();

				if (privileges != null) {
					for (Privilege privilege : privileges) {
						SimpleGrantedAuthority roleAuthority = new SimpleGrantedAuthority(
								privilege.getPrivilegeName());

						listRoles.add(roleAuthority);
					}
				}
			}
			final boolean accountNonLocked = (author.getIsLocked() == 'Y');
			final boolean isActive = (author.getIsActive() == 'Y');
			// set both if account is active
			final boolean accountNonExpired, credentialsNonExpired, enabled;
			accountNonExpired = credentialsNonExpired = enabled = isActive;
			/**
			 * User(String username, String password, boolean enabled, boolean
			 * accountNonExpired, boolean credentialsNonExpired, boolean
			 * accountNonLocked, Collection<? extends GrantedAuthority>
			 * authorities) {
			 */
			final String authorUsername = author.getAuthorUserName();
			final String authorPassword = author.getPassword();
			UserDetails user = new User(authorUsername, authorPassword,
					enabled, accountNonExpired, credentialsNonExpired,
					accountNonLocked, listRoles);
			return user;
		} else {
			throw new UsernameNotFoundException("Username not found");
		}

	}

	/***
	 * Validation {@link AuthorRegistrationDto} with @valid at controller-aspect
	 * level
	 */

	public void registerUser(AuthorRegistrationDto authorRegistrationDto)
			throws UserAlreadyExistsException {
		AuthorLoginDto authorLoginDto = null;
		Preconditions.checkArgument(null != authorRegistrationDto, "Registration form cannot be empty");
		authorLoginDto = authorRegistrationDto.getAuthorLoginDto();
		Preconditions.checkArgument(null != authorLoginDto, "Login details cannot be empty");
		final boolean usernameFlag, emailFlag, mobileNumberFlag;
		usernameFlag=checkIfUserNameAlreadyExists(authorLoginDto.getAuthorDto()
				.getAuthorUserName());
		Preconditions.checkArgument(!usernameFlag, "Username is taken");
		emailFlag=checkIfUserEmailAlreadyExists(authorLoginDto.getAuthorDto()
				.getAuthorEmail());
		Preconditions.checkArgument(!emailFlag,"Email id is already registered");
		mobileNumberFlag=checkIfUserMobileNumberExists(authorRegistrationDto
				.getAuthorLoginDto().getAuthorMobileNumber());
		Preconditions.checkArgument(!mobileNumberFlag, "Mobile number is already registered");
		if (!(usernameFlag||emailFlag||mobileNumberFlag)) {

			PersonalDetails personalDetails = convertToPersonalDetailsEntity(
					authorRegistrationDto, new PersonalDetails());
			Author author = convertToAuthorEntity(
					authorLoginDto.getAuthorDto(), new Author());
			author.setMobileNumber(authorLoginDto.getAuthorMobileNumber());
			author.setPassword(passwordEncoder.encode(authorLoginDto
					.getAdminPassword()));

			personalDetails.setAuthor(author);
			// This is mandatory
			customUserDao.save(author);
			personalDetailsDao.save(personalDetails);

		} else {
			throw new UserAlreadyExistsException(
					"User name or email already exists");
		}

	}


	/**
	 * This method return true if username exists already
	 * 
	 * @param userName
	 * @return
	 */
	private boolean checkIfUserNameAlreadyExists(String userName) {
		Preconditions.checkArgument(null != userName, "Username cannot be empty");

		return (customUserDao.findByAuthorUserName(userName) != null);
	}

	/**
	 * This method return true if username exists already
	 * 
	 * @param userName
	 * @return
	 */
	private boolean checkIfUserEmailAlreadyExists(String email) {
		Preconditions.checkArgument(null != email, "Email address cannot be empty");

		return (customUserDao.findByAuthorEmail(email) != null);
	}

	private boolean checkIfUserMobileNumberExists(String mobileNumber) {
		Preconditions.checkArgument(null != mobileNumber, "Mobile number cannot be empty");
		return (customUserDao.findByMobileNumber(mobileNumber) != null);
	}

	public void updateUser(AuthorRegistrationDto authorRegistrationDto) {
		// TODO Auto-generated method stub
		
	}

	public void resetPassword(String userNameOrEmail, String password) {
		// TODO Auto-generated method stub
		
	}
}
