package in.brewcode.api.auth.server.service.impl;

import static in.brewcode.api.service.common.ServiceUtils.convertToAuthorEntity;
import in.brewcode.api.auth.server.service.CustomUserDetailsService;
import in.brewcode.api.dto.AuthorLoginDto;
import in.brewcode.api.dto.AuthorRegistrationDto;
import in.brewcode.api.exception.UserAlreadyExistsException;
import in.brewcode.api.persistence.dao.IAdminAuthorDao;
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
public class CustomUserDetailsServiceImpl implements
		CustomUserDetailsService {

	@Autowired
	IAdminAuthorDao adminAuthorDao;

	@Autowired
	IPersonalDetailsDao personalDetailsDao;

	@Autowired
	PasswordEncoder passwordEncoder;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		Author author = adminAuthorDao.findByAuthorUserName(username);
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
		Preconditions.checkNotNull(authorRegistrationDto);
		AuthorLoginDto authorLoginDto = authorRegistrationDto
				.getAuthorLoginDto();
		Preconditions.checkNotNull(authorLoginDto);
		if (!(checkIfUserEmailAlreadyExists(authorLoginDto.getAuthorDto()
				.getAuthorEmail())
				|| checkIfUserNameAlreadyExists(authorLoginDto.getAuthorDto()
						.getAuthorUserName()) || checkIfUserMobileNumberExists(authorRegistrationDto
				.getAuthorLoginDto().getAuthorMobileNumber()))) {

			PersonalDetails personalDetails = convertToPersonalDetailsEntity(
					authorRegistrationDto, new PersonalDetails());
			Author author = convertToAuthorEntity(
					authorLoginDto.getAuthorDto(), new Author());
			author.setMobileNumber(authorLoginDto.getAuthorMobileNumber());
			author.setPassword(passwordEncoder.encode(authorLoginDto
					.getAdminPassword()));

			personalDetails.setAuthor(author);
			// This is mandatory
			adminAuthorDao.save(author);
			personalDetailsDao.save(personalDetails);

		} else {
			throw new UserAlreadyExistsException(
					"User name or email already exists");
		}

	}

	public void updateUser(AuthorRegistrationDto authorRegistrationDto) {

	}

	public void resetPassword(String userNameOrEmail, String password) {

	}

	/**
	 * Doesn't convert {@link Author} field of {@link AuthorRegistrationDto}
	 * 
	 * @param ard
	 * @param pde
	 * @return
	 */
	private PersonalDetails convertToPersonalDetailsEntity(
			AuthorRegistrationDto ard, PersonalDetails pde) {
		Preconditions.checkNotNull(ard);
		pde.setAddress(ard.getAddress());
		pde.setDateOfBirth(ard.getAdminDateOfBirth());
		pde.setFirstName(ard.getAdminFirstName());
		pde.setLastName(ard.getAdminLastName());

		return pde;
	}

	/**
	 * This method return true if username exists already
	 * 
	 * @param userName
	 * @return
	 */
	private boolean checkIfUserNameAlreadyExists(String userName) {
		Preconditions.checkNotNull(userName);

		return (adminAuthorDao.findByAuthorUserName(userName) != null);
	}

	/**
	 * This method return true if username exists already
	 * 
	 * @param userName
	 * @return
	 */
	private boolean checkIfUserEmailAlreadyExists(String email) {
		Preconditions.checkNotNull(email);

		return (adminAuthorDao.findByAuthorEmail(email) != null);
	}

	private boolean checkIfUserMobileNumberExists(String mobileNumber) {
		Preconditions.checkNotNull(mobileNumber);
		return (adminAuthorDao.findByMobileNumber(mobileNumber) != null);
	}
}
