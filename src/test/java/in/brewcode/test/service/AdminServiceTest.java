package in.brewcode.test.service;

import in.brewcode.api.config.PersistenceConfig;
import in.brewcode.api.config.SecurityConfig;
import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.dto.PrivilegeDto;
import in.brewcode.api.dto.RoleDto;
import in.brewcode.api.exception.PrivilegeAlreadyExistsException;
import in.brewcode.api.exception.PrivilegeNotFoundException;
import in.brewcode.api.exception.RoleAlreadyExistsException;
import in.brewcode.api.exception.RoleInUseException;
import in.brewcode.api.exception.RoleNotFoundException;
import in.brewcode.api.exception.UserAlreadyExistsException;
import in.brewcode.api.exception.UserNotFoundException;
import in.brewcode.api.persistence.dao.IAdminAuthorDao;
import in.brewcode.api.persistence.dao.IPrivilegeDao;
import in.brewcode.api.persistence.dao.IRoleDao;
import in.brewcode.api.persistence.entity.Author;
import in.brewcode.api.persistence.entity.Privilege;
import in.brewcode.api.persistence.entity.Role;
import in.brewcode.api.service.IAdminService;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class, SecurityConfig.class })
// @Transactional
public class AdminServiceTest {

	private static Logger log = Logger.getLogger(AdminServiceTest.class);
	@Autowired
	private IAdminService adminService;

	@Autowired
	private IAdminAuthorDao adminAuthorDao;

	@Autowired
	private IPrivilegeDao privilegeDao;
	@Autowired
	private IRoleDao roleDao;
	private String TEST_USER_NAME;

	private String TEST_USER_EMAIL;

	private String TEST_PRIVILEGE_NAME;

	private String TEST_ROLE_NAME;

	@Before
	public void init() {
		TEST_ROLE_NAME = RandomStringUtils.randomAlphabetic(7);
		TEST_PRIVILEGE_NAME = RandomStringUtils.randomAlphabetic(7);
		TEST_USER_NAME = RandomStringUtils.randomAlphabetic(7);
		TEST_USER_EMAIL = RandomStringUtils.randomAlphanumeric(7) + "@mail.com";
		log.debug("Inside init method");

		final PrivilegeDto pd = new PrivilegeDto();
		pd.setPrivilegeName(TEST_PRIVILEGE_NAME);
		try {
			adminService.addPrivilege(pd);
		} catch (PrivilegeAlreadyExistsException e1) {
			log.error(e1.getMessage(), e1);
		}

		RoleDto rd = new RoleDto();
		rd.setRoleName(TEST_ROLE_NAME);
		rd.setPrivileges(new HashSet<PrivilegeDto>() {
			{
				add(pd);
			}
		});
		try {
			adminService.addRole(rd);
		} catch (PrivilegeNotFoundException e1) {
			log.error(e1.getMessage(), e1);

		} catch (RoleAlreadyExistsException e1) {
			log.error(e1.getMessage(), e1);
		}
		AuthorDto ad = new AuthorDto();
		ad.setAuthorUserName(TEST_USER_NAME);
		ad.setAuthorEmail(TEST_USER_EMAIL);
		log.debug("User name for this test: " + ad.getAuthorUserName());
		try {
			adminService.createAuthor(ad);
			// log.error("test");
		} catch (UserAlreadyExistsException e) {
			log.error(e.getMessage(), e);
		}

		log.debug("Created user:" + ad.getAuthorUserName());
	}

	@Test
	public void findByUserNameWithExistingUsername_Noerrors()
			throws UserNotFoundException {

		AuthorDto ad = adminService.findByUserName(TEST_USER_NAME);
		log.debug(ad.toString());
		Assert.notNull(ad);
	}

	@Test(expected = UserNotFoundException.class)
	public void findByInvalidUserName_expectUserNotFoundException()
			throws UserNotFoundException {
		// Some random username. init() with 7 letters and search here with 4
		AuthorDto ad = adminService.findByUserName(RandomStringUtils
				.randomAlphabetic(4));
		Assert.isNull(ad);
	}

	@Test(expected = IllegalArgumentException.class)
	public void findByNullOrEmptyUserName_expectUserNotFoundException()
			throws UserNotFoundException {
		adminService.findByUserName(null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void findByEmptyUserName_expectUserNotFoundException()
			throws UserNotFoundException {
		adminService.findByUserName("");

	}

	/**
	 * Check if user is locked by default on creation
	 * 
	 * @throws UserNotFoundException
	 * @throws UserAlreadyExistsException
	 */
	@Test
	public void checkLockExistingUser_NoErrors()
			throws UserAlreadyExistsException {
		AuthorDto ad = new AuthorDto();
		ad.setAuthorEmail(RandomStringUtils.randomAlphanumeric(7)
				+ "@gmail.com");
		ad.setAuthorUserName(RandomStringUtils.randomAlphabetic(6));
		adminService.createAuthor(ad);
		Author a = adminAuthorDao.findByAuthorUserName(ad.getAuthorUserName());
		Assert.isTrue(a.getIsLocked() == 'Y');
	}

	@Test
	public void lockExistingUser_NoErrors() throws UserNotFoundException {
		adminService.lockAuthor(TEST_USER_NAME);
		Author ad = adminAuthorDao.findByAuthorUserName(TEST_USER_NAME);

		Assert.isTrue(ad.getIsLocked() == 'Y');
	}

	@Test
	public void unlockExistingUser_NoErrors() throws UserNotFoundException {
		adminService.unlockAuthor(TEST_USER_NAME);
		Author ad = adminAuthorDao.findByAuthorUserName(TEST_USER_NAME);

		Assert.isTrue(ad.getIsLocked() == 'N');
	}

	@Test
	public void deleteAuthor_NoErrors() throws UserNotFoundException {
		adminService.deleteAuthor(TEST_USER_NAME);
		log.debug("this.deleteAuthor_NoErrors() method");
		Assert.isNull(adminAuthorDao.findByAuthorUserName(TEST_USER_NAME));

	}

	@Test(expected = UserNotFoundException.class)
	public void deleteAuthorWhenInvalidUserName_Exception()
			throws UserNotFoundException {
		adminService.deleteAuthor(RandomStringUtils.randomAlphanumeric(6));

	}

	@Test
	public void addPrivilege_NoErrors() throws PrivilegeAlreadyExistsException {
		PrivilegeDto pd = new PrivilegeDto();
		pd.setPrivilegeName(RandomStringUtils.randomAlphabetic(5));
		adminService.addPrivilege(pd);
	}

	@Test(expected = PrivilegeAlreadyExistsException.class)
	public void addExistingPrivilegeName_Errors()
			throws PrivilegeAlreadyExistsException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege.toLowerCase());
		adminService.addPrivilege(pd);

		pd.setPrivilegeName(privilege.toUpperCase());
		adminService.addPrivilege(pd);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addNullPrivilegeName_Error()
			throws PrivilegeAlreadyExistsException {

		adminService.addPrivilege(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addEmptyPrivilegeName_Error()
			throws PrivilegeAlreadyExistsException {

		adminService.addPrivilege(new PrivilegeDto());
	}

	@Test
	public void addRole_noError() throws PrivilegeAlreadyExistsException,
			PrivilegeNotFoundException, RoleAlreadyExistsException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);
	}

	@Test
	public void addRoleWithEmptyPrivileges_noError()
			throws PrivilegeNotFoundException, RoleAlreadyExistsException {
		RoleDto roleDto = new RoleDto();

		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(6));
		// roleDto is not set with any privileges
		adminService.addRole(roleDto);

	}

	@Test(expected = PrivilegeNotFoundException.class)
	public void addRolewithNonExistingPrivilege_noError()
			throws PrivilegeNotFoundException, RoleAlreadyExistsException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		// not in db
		// adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addEmptyRoleWithoutRoleName_Error()
			throws PrivilegeNotFoundException, RoleAlreadyExistsException {
		RoleDto roleDto = new RoleDto();
		adminService.addRole(roleDto);

	}

	@Test(expected = RoleAlreadyExistsException.class)
	public void addExistingRoleName_Error()
			throws PrivilegeAlreadyExistsException, PrivilegeNotFoundException,
			RoleAlreadyExistsException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);
		// add again
		adminService.addRole(roleDto);

	}

	@Test
	public void assignRole_NoError() throws PrivilegeAlreadyExistsException,
			PrivilegeNotFoundException, UserNotFoundException,
			RoleNotFoundException, RoleAlreadyExistsException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);

		// Assign newly added role to user created in init method
		adminService.assignRoletoAuthor(TEST_USER_NAME, roleDto.getRoleName());

	}

	@Test
	public void assignSameRoletoUserTwice_NoError()
			throws UserNotFoundException, RoleNotFoundException,
			PrivilegeNotFoundException, PrivilegeAlreadyExistsException,
			RoleAlreadyExistsException {

		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);

		// Assign newly added role to user created in init method, 1st time
		adminService.assignRoletoAuthor(TEST_USER_NAME, roleDto.getRoleName());

		// Same role assigned twice
		adminService.assignRoletoAuthor(TEST_USER_NAME, roleDto.getRoleName());

	}

	@Test(expected = IllegalArgumentException.class)
	public void assignNullRoletoUser_Error() throws UserNotFoundException,
			RoleNotFoundException {
		adminService.assignRoletoAuthor(TEST_USER_NAME, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void assignEmptyRoletoUser_Error() throws UserNotFoundException,
			RoleNotFoundException {
		adminService.assignRoletoAuthor(TEST_USER_NAME, "");

	}

	@Test(expected = RoleNotFoundException.class)
	public void assignInvalidRoletoUser_Error() throws UserNotFoundException,
			RoleNotFoundException {
		adminService.assignRoletoAuthor(TEST_USER_NAME,
				RandomStringUtils.randomAlphabetic(5));

	}

	@Test(expected = UserNotFoundException.class)
	public void assignRoletoInvalidUser_Error() throws UserNotFoundException,
			RoleNotFoundException, PrivilegeAlreadyExistsException,
			PrivilegeNotFoundException, RoleAlreadyExistsException {

		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);

		adminService.assignRoletoAuthor(RandomStringUtils.randomAlphabetic(5),
				roleDto.getRoleName());

	}

	@Test
	public void findAllRoles_NoError() throws PrivilegeNotFoundException,
			RoleAlreadyExistsException, PrivilegeAlreadyExistsException {


		List<RoleDto> list = adminService.getAllRoles();
		
		Assert.notEmpty(list);
	}

	@Test
	public void findAllPrivileges_NoError()
			throws PrivilegeAlreadyExistsException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);

		List<PrivilegeDto> list = adminService.getAllPrivileges();

		Assert.notEmpty(list);
	}

	/**
	 * Add and delete(soft) privileges. Find list is empty
	 * 
	 * @throws PrivilegeAlreadyExistsException
	 * @throws PrivilegeNotFoundException
	 */
	@Test
	public void findAllPrivilegesWithDeletedPrivileges_Error()
			throws PrivilegeAlreadyExistsException, PrivilegeNotFoundException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);

		adminService.deletePrivilege(privilege);
		List<PrivilegeDto> list = adminService.getAllPrivileges();

		Assert.isTrue(!list.contains(privilegeDao
				.findByPrivilegeNameIgnoreCase(privilege)));
	}

	@Test(expected = RoleNotFoundException.class)
	public void deleteInvalidRoleName_Error() throws RoleNotFoundException,
			RoleInUseException {

		adminService.deleteRole(RandomStringUtils.randomAlphabetic(3));
	}

	@Test
	public void deleteRoleNonAssigned_NoError()
			throws PrivilegeAlreadyExistsException, RoleNotFoundException,
			PrivilegeNotFoundException, RoleAlreadyExistsException,
			RoleInUseException {

		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);

		// Assign newly added role to user created in init method, 1st time
		// adminService.assignRoletoAuthor(TEST_USER_NAME,
		// roleDto.getRoleName());

		adminService.deleteRole(roleDto.getRoleName());
		Assert.isNull(roleDao.findByRoleNameIgnoreCase(roleDto.getRoleName()));
	}

	@Test(expected = RoleInUseException.class)
	public void deleteRoleAssignedToUser_Error()
			throws PrivilegeAlreadyExistsException, RoleNotFoundException,
			PrivilegeNotFoundException, RoleAlreadyExistsException,
			UserNotFoundException, RoleInUseException {

		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);

		// Assign newly added role to user created in init method, 1st time
		adminService.assignRoletoAuthor(TEST_USER_NAME, roleDto.getRoleName());

		adminService.deleteRole(roleDto.getRoleName());

	}

	@Test
	public void deleteRoleAssignedOnlyToInactiveUser_Error()
			throws PrivilegeAlreadyExistsException, RoleNotFoundException,
			PrivilegeNotFoundException, RoleAlreadyExistsException,
			UserNotFoundException, RoleInUseException {

		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		RoleDto roleDto1 = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		roleDto1.setRoleName(RandomStringUtils.randomAlphabetic(5));

		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		roleDto1.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);
		adminService.addRole(roleDto1);
		// Assign newly added role to user created in init method, 1st time
		adminService.assignRoletoAuthor(TEST_USER_NAME, roleDto.getRoleName());
		adminService.deleteAuthor(TEST_USER_NAME);
		log.debug("Deleted user: " + TEST_USER_NAME);
		adminService.deleteRole(roleDto.getRoleName());
		log.debug("Deleted role: " + roleDto.getRoleName());
		Role role1 = roleDao.findByRoleNameIgnoreCase(roleDto.getRoleName());
		Assert.isNull(role1);

	}

	@Test
	public void removeAuthorRole_NoError()
			throws PrivilegeAlreadyExistsException, PrivilegeNotFoundException,
			RoleAlreadyExistsException, UserNotFoundException,
			RoleNotFoundException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		RoleDto roleDto1 = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		roleDto1.setRoleName(RandomStringUtils.randomAlphabetic(5));

		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		roleDto1.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);
		// Assign newly added role to user created in init method, 1st time
		adminService.assignRoletoAuthor(TEST_USER_NAME, roleDto.getRoleName());

		adminService.removeRoleOfAuthor(TEST_USER_NAME, roleDto.getRoleName());

	}

	@Test(expected = RoleNotFoundException.class)
	public void removeAuthorNonExistingRole_Error()
			throws PrivilegeAlreadyExistsException, PrivilegeNotFoundException,
			RoleAlreadyExistsException, UserNotFoundException,
			RoleNotFoundException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		RoleDto roleDto1 = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		roleDto1.setRoleName(RandomStringUtils.randomAlphabetic(5));

		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		roleDto1.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);
		// Assign newly added role to user created in init method, 1st time
		adminService.assignRoletoAuthor(TEST_USER_NAME, roleDto.getRoleName());
		// Removed role of author here
		adminService.removeRoleOfAuthor(TEST_USER_NAME, roleDto.getRoleName());

		log.debug(roleDao.findByRoleNameIgnoreCase(roleDto.getRoleName())
				.getRoleName() + " Author removed. And role not deleted");
		// Removed again only to throw RoleNotFoundException
		adminService.removeRoleOfAuthor(TEST_USER_NAME, roleDto.getRoleName());

	}

	@Test(expected = UserNotFoundException.class)
	public void removeNonExistingAuthorButRole_Error()
			throws PrivilegeAlreadyExistsException, PrivilegeNotFoundException,
			RoleAlreadyExistsException, UserNotFoundException,
			RoleNotFoundException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		RoleDto roleDto1 = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		roleDto1.setRoleName(RandomStringUtils.randomAlphabetic(5));

		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		roleDto1.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);
		// Assign newly added role to user created in init method, 1st time
		adminService.assignRoletoAuthor(TEST_USER_NAME, roleDto.getRoleName());

		// Delete author.
		adminService.deleteAuthor(TEST_USER_NAME);
		// Some invalid username, only to throw RoleNotFoundException
		adminService.removeRoleOfAuthor(TEST_USER_NAME, roleDto.getRoleName());

	}

	@Test(expected = IllegalArgumentException.class)
	public void removeAuthorRoleWithRoleNameNull_Error()
			throws PrivilegeAlreadyExistsException, PrivilegeNotFoundException,
			RoleAlreadyExistsException, UserNotFoundException,
			RoleNotFoundException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		RoleDto roleDto1 = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		roleDto1.setRoleName(RandomStringUtils.randomAlphabetic(5));

		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		roleDto1.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);
		// Assign newly added role to user created in init method, 1st time
		adminService.assignRoletoAuthor(TEST_USER_NAME, roleDto.getRoleName());
		// Some invalid username, only to throw IllegalArgumentException
		adminService.removeRoleOfAuthor(TEST_USER_NAME, "");

	}

	@Test
	public void deleteExistingPrivilege_NoError()
			throws PrivilegeAlreadyExistsException, PrivilegeNotFoundException {
		PrivilegeDto pd = new PrivilegeDto();
		final String priv = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(priv);

		adminService.addPrivilege(pd);
		// Now delete privilege

		adminService.deletePrivilege(priv);
		Assert.isNull(privilegeDao.findByPrivilegeNameIgnoreCase(priv));

	}

	@Test
	public void deletePrivilegeOfAssignedRole_NoError()
			throws PrivilegeAlreadyExistsException, PrivilegeNotFoundException,
			RoleAlreadyExistsException, UserNotFoundException,
			RoleNotFoundException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		RoleDto roleDto1 = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		roleDto1.setRoleName(RandomStringUtils.randomAlphabetic(5));

		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		roleDto1.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);
		// Assign newly added role to user created in init method, 1st time
		adminService.assignRoletoAuthor(TEST_USER_NAME, roleDto.getRoleName());

		adminService.deletePrivilege(privilege);
		Privilege priv = privilegeDao.findByPrivilegeNameIgnoreCase(privilege);
		Role role = roleDao.findByRoleNameIgnoreCase(roleDto.getRoleName());
		Assert.isTrue(!role.getRolePrivileges().contains(priv));
		Assert.isNull(priv);
	}

	@Test(expected = PrivilegeNotFoundException.class)
	public void deleteNonExistingPrivilege_Error()
			throws PrivilegeNotFoundException {
		PrivilegeDto pd = new PrivilegeDto();
		final String priv = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(priv);

		adminService.deletePrivilege(priv);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteNullPrivilege_Error() throws PrivilegeNotFoundException {

		adminService.deletePrivilege(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteEmptyPrivilege_Error() throws PrivilegeNotFoundException {

		adminService.deletePrivilege("");
	}

	@Test
	public void updateRoleName_NoErrors() throws PrivilegeNotFoundException,
			RoleAlreadyExistsException, PrivilegeAlreadyExistsException,
			RoleNotFoundException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		RoleDto roleDto1 = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		roleDto1.setRoleName(RandomStringUtils.randomAlphabetic(5));

		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		roleDto1.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);

		adminService.updateRoleName(roleDto.getRoleName(),
				RandomStringUtils.randomAlphabetic(5));

	}

	@Test(expected = RoleNotFoundException.class)
	public void updateInactiveRoleName_Error()
			throws PrivilegeNotFoundException, RoleAlreadyExistsException,
			PrivilegeAlreadyExistsException, RoleNotFoundException,
			RoleInUseException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		RoleDto roleDto1 = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		roleDto1.setRoleName(RandomStringUtils.randomAlphabetic(5));

		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		roleDto1.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);
		adminService.deleteRole(roleDto.getRoleName());
		adminService.updateRoleName(roleDto.getRoleName(),
				RandomStringUtils.randomAlphabetic(5));

	}

	@Test
	public void updateRoleNameInUse_NoError()
			throws PrivilegeNotFoundException, RoleAlreadyExistsException,
			PrivilegeAlreadyExistsException, RoleNotFoundException,
			RoleInUseException, UserNotFoundException {
		PrivilegeDto pd = new PrivilegeDto();
		final String privilege = RandomStringUtils.randomAlphabetic(5);
		pd.setPrivilegeName(privilege);
		adminService.addPrivilege(pd);
		RoleDto roleDto = new RoleDto();
		RoleDto roleDto1 = new RoleDto();
		roleDto.setRoleName(RandomStringUtils.randomAlphabetic(5));
		roleDto1.setRoleName(RandomStringUtils.randomAlphabetic(5));

		Set<PrivilegeDto> privilegeDtos = new LinkedHashSet<PrivilegeDto>();
		privilegeDtos.add(pd);
		roleDto.setPrivileges(privilegeDtos);
		roleDto1.setPrivileges(privilegeDtos);
		adminService.addRole(roleDto);
		adminService.assignRoletoAuthor(TEST_USER_NAME, roleDto.getRoleName());
		adminService.updateRoleName(roleDto.getRoleName(),
				RandomStringUtils.randomAlphabetic(5));

	}

	@Test(expected = RoleNotFoundException.class)
	public void updateNonExistingRoleName_Error()
			throws PrivilegeNotFoundException, RoleAlreadyExistsException,
			PrivilegeAlreadyExistsException, RoleNotFoundException,
			RoleInUseException, UserNotFoundException {
		adminService.updateRoleName(RandomStringUtils.randomAlphabetic(5),
				RandomStringUtils.randomAlphabetic(5));

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateNullRoleName_Error() throws PrivilegeNotFoundException,
			RoleAlreadyExistsException, PrivilegeAlreadyExistsException,
			RoleNotFoundException, RoleInUseException, UserNotFoundException {
		adminService
				.updateRoleName(null, RandomStringUtils.randomAlphabetic(5));

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateEmptyRoleName_Error() throws PrivilegeNotFoundException,
			RoleAlreadyExistsException, PrivilegeAlreadyExistsException,
			RoleNotFoundException, RoleInUseException, UserNotFoundException {
		adminService.updateRoleName("", RandomStringUtils.randomAlphabetic(5));

	}

	/*
	 * 
	 * public void removeRoleOfAuthor(String authorUserName, String roleName)
	 * throws UserNotFoundException,
	 * in.brewcode.api.exception.RoleNotFoundException;
	 * 
	 * public List<RoleDto> getAllRoles() throws RoleNotFoundException;
	 * 
	 * public List<PrivilegeDto> getAllPrivileges() throws
	 * PrivilegeNotFoundException;
	 * 
	 * public void updatePrivilegesOfRole(RoleDto roleDto) throws
	 * RoleNotFoundException, PrivilegeNotFoundException;
	 * 
	 * 
	 * public void updateRoleName(String oldRoleName, String newRoleName) throws
	 * RoleAlreadyExistsException, RoleNotFoundException;
	 * 
	 * public void deleteRole(String roleName) throws RoleNotFoundException;
	 * 
	 * public void deletePrivilege(String privilegeName) throws
	 * PrivilegeNotFoundException;
	 * 
	 * public List<AuthorWithRoleDto> findAllAuthors();
	 */

}
