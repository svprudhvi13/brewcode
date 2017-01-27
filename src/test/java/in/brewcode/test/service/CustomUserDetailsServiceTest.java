package in.brewcode.test.service;

import in.brewcode.api.auth.server.service.CustomUserDetailsService;
import in.brewcode.api.config.PersistenceConfig;
import in.brewcode.api.config.SecurityConfig;
import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.dto.AuthorLoginDto;
import in.brewcode.api.dto.AuthorRegistrationDto;
import in.brewcode.api.exception.UserAlreadyExistsException;
import in.brewcode.api.persistence.entity.PersonalDetails;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {
		PersistenceConfig.class, SecurityConfig.class })
@Transactional
public class CustomUserDetailsServiceTest {

	@Autowired
	private CustomUserDetailsService cuds;

	@Test
	public void whenNewUserRegisters_NoError()
			throws UserAlreadyExistsException {
		AuthorRegistrationDto ard = new AuthorRegistrationDto();
		AuthorDto ad = new AuthorDto();
		AuthorLoginDto ald = new AuthorLoginDto();

		ard.setAddress(RandomStringUtils.randomAlphabetic(100));
		ard.setAdminDateOfBirth(new Date());
		ard.setAdminFirstName(RandomStringUtils.randomAlphabetic(10));
		ard.setAdminLastName(RandomStringUtils.randomAlphabetic(10));
		ard.setConfirmPassword("password");
		ad.setAuthorUserName(RandomStringUtils.randomAlphanumeric(6));
		ad.setAuthorEmail(RandomStringUtils.randomAlphanumeric(6)
				+ "@email.com");
		ald.setAdminPassword("password");
		ald.setAuthorDto(ad);
		ald.setAuthorMobileNumber(RandomStringUtils.randomNumeric(10));
		ard.setAuthorLoginDto(ald);

		cuds.registerUser(ard);

	}

	@Before
	public void init() throws UserAlreadyExistsException {
		AuthorRegistrationDto ard = new AuthorRegistrationDto();
		AuthorDto ad = new AuthorDto();
		AuthorLoginDto ald = new AuthorLoginDto();

		ard.setAddress(RandomStringUtils.randomAlphabetic(100));
		ard.setAdminDateOfBirth(new Date());
		ard.setAdminFirstName(RandomStringUtils.randomAlphabetic(10));
		ard.setAdminLastName(RandomStringUtils.randomAlphabetic(10));
		ard.setConfirmPassword("password");
		ad.setAuthorUserName("scholes18");
		ad.setAuthorEmail(RandomStringUtils.randomAlphanumeric(6)
				+ "@email.com");
		ald.setAdminPassword("password");
		ald.setAuthorDto(ad);
		ald.setAuthorMobileNumber(RandomStringUtils.randomNumeric(10));
		ard.setAuthorLoginDto(ald);
		cuds.registerUser(ard);

	}

	@Test(expected = UserAlreadyExistsException.class)
	public void whenExistingUserNameRegister_Exception()
			throws UserAlreadyExistsException {
		AuthorRegistrationDto ard = new AuthorRegistrationDto();
		AuthorDto ad = new AuthorDto();
		AuthorLoginDto ald = new AuthorLoginDto();

		ard.setAddress(RandomStringUtils.randomAlphabetic(100));
		ard.setAdminDateOfBirth(new Date());
		ard.setAdminFirstName(RandomStringUtils.randomAlphabetic(10));
		ard.setAdminLastName(RandomStringUtils.randomAlphabetic(10));
		ard.setConfirmPassword("password");
		ad.setAuthorUserName("scholes18");
		ad.setAuthorEmail(RandomStringUtils.randomAlphanumeric(6)
				+ "@gmail.com");
		ald.setAdminPassword("password");
		ald.setAuthorDto(ad);
		ald.setAuthorMobileNumber(RandomStringUtils.randomNumeric(10));
		ard.setAuthorLoginDto(ald);
		cuds.registerUser(ard);

		// cuds.registerUser(ard);
	}

	@Test
	public void getExistingUserDetails_NoError() {
		UserDetails userDetails = cuds.loadUserByUsername("scholes18");
	}

}
