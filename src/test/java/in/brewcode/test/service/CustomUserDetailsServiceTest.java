package in.brewcode.test.service;

import in.brewcode.api.auth.server.service.ICustomUserDetailsService;
import in.brewcode.api.config.PersistenceConfig;
import in.brewcode.api.config.SecurityConfig;
import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.dto.AuthorLoginDto;
import in.brewcode.api.dto.AuthorRegistrationDto;
import in.brewcode.api.exception.UserAlreadyExistsException;
import in.brewcode.api.exception.UserNotFoundException;

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
	//Used in @Before method. static as it has to load first
	private static final String username=RandomStringUtils.randomAlphanumeric(7);

	@Autowired
	private ICustomUserDetailsService cuds;

	@Test
	//@Ignore
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
		//From this class' static variable
		ad.setAuthorUserName(CustomUserDetailsServiceTest.username);
		ad.setAuthorEmail(RandomStringUtils.randomAlphanumeric(6)
				+ "@email.com");
		ald.setAdminPassword("password");
		ald.setAuthorDto(ad);
		ald.setAuthorMobileNumber(RandomStringUtils.randomNumeric(10));
		ard.setAuthorLoginDto(ald);
		cuds.registerUser(ard);

	}

	@Test(expected = UserAlreadyExistsException.class)
	//@Ignore
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
		ad.setAuthorUserName(CustomUserDetailsServiceTest.username);
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
public void getExistingUserProfile_NoError() throws UserNotFoundException{
	AuthorRegistrationDto ard = cuds.getUserProfile(CustomUserDetailsServiceTest.username);
	System.out.println(ard.getAuthorLoginDto().getAuthorDto().getAuthorUserName() + " "+ard.getAuthorLoginDto().getAuthorDto().getAuthorEmail());
	org.springframework.util.Assert.notNull(ard, "Profile found");
	
}
	@Test
	//@Ignore
	public void getExistingUserDetails_NoError() {
		UserDetails userDetails = cuds.loadUserByUsername(CustomUserDetailsServiceTest.username);
	
	org.springframework.util.Assert.notNull(userDetails, " found");
	}
	
	@Test(expected=IllegalArgumentException.class)
	//@Ignore
	public void whenUserisSavedWithIncomplete_Error() throws UserAlreadyExistsException {
		AuthorRegistrationDto ard = new AuthorRegistrationDto();
		cuds.registerUser(ard);
	}
}
