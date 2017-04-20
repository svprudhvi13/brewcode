package in.brewcode.api.web;

import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.dto.AuthorWithRoleDto;
import in.brewcode.api.dto.PrivilegeDto;
import in.brewcode.api.dto.RoleDto;
import in.brewcode.api.exception.PrivilegeAlreadyExistsException;
import in.brewcode.api.exception.PrivilegeNotFoundException;
import in.brewcode.api.exception.RoleAlreadyExistsException;
import in.brewcode.api.exception.RoleInUseException;
import in.brewcode.api.exception.RoleNotFoundException;
import in.brewcode.api.exception.UserAlreadyExistsException;
import in.brewcode.api.exception.UserNotFoundException;
import in.brewcode.api.service.IAdminService;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Preconditions;


@RequestMapping(value="/admin")
public class AdminController {

	private static Logger logger = Logger.getLogger(AdminController.class);

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	@Autowired
	private IAdminService adminService;

	/**
	 * No need to use this method. As users register themselves from
	 * {@link UserRegistrationController}
	 * 
	 * @param authorDto
	 * @param response
	 * @throws UserAlreadyExistsException
	 */
	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/authors/create", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createAuthor(@RequestBody AuthorDto authorDto)
			throws UserAlreadyExistsException {

		Preconditions.checkNotNull(authorDto, "");
		adminService.createAuthor(authorDto);
		logger.debug("Author created");

	}

	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/authors/lock", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void lockUsers(@RequestBody String[] arr)
			throws UserNotFoundException {
		Preconditions.checkArgument(arr != null && arr.length > 0,
				"Usernames List cannot be empty");

		for (String username : arr) {
			adminService.lockAuthor(username);
		}

	}

	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/authors/unlock", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void unlockUsers(@RequestBody String[] arr)
			throws UserNotFoundException {
		Preconditions.checkArgument(arr != null && arr.length > 0,
				"Usernames List cannot be empty");

		for (String username : arr) {
			adminService.unlockAuthor(username);
		}

	}

	/*
	 * @RequestMapping(value = "/author/update/", method = RequestMethod.POST)
	 * 
	 * @ResponseStatus(value = HttpStatus.CREATED)
	 * 
	 * @ResponseBody public void updateAuthor(@RequestBody AuthorDto authorDto,
	 * HttpServletResponse response) {
	 * 
	 * Preconditions.checkNotNull(authorDto);
	 * adminService.updateAuthor(authorDto); logger.debug("Author updated");
	 * 
	 * }
	 */

	@PreAuthorize("#oauth2.hasAnyScope('admin_app') and (hasRole('ADMIN') or hasRole('USER'))")
	@RequestMapping(value = "/authors/{username}", method = RequestMethod.GET)
	@ResponseBody
	public AuthorDto findAuthorByUserName(
			@PathVariable(value = "username") final String username)
			throws UserNotFoundException {
		AuthorDto authorDto = null;
		authorDto = adminService.findByUserName(username);
		return authorDto;
	}

	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/authors", method = RequestMethod.GET)
	@ResponseBody
	public List<AuthorWithRoleDto> findAllAuthors() {
		List<AuthorWithRoleDto> listAuthors = null;

		listAuthors = adminService.findAllAuthors();

		return listAuthors;

	}

	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/privileges/", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createPrivilege(@RequestBody PrivilegeDto privilegeDto)
			throws PrivilegeAlreadyExistsException {
		Preconditions.checkNotNull(privilegeDto,
				"Invalid input format, privilege cannot be null");

		adminService.addPrivilege(privilegeDto);

	}

	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/privileges", method = RequestMethod.GET)
	public ResponseEntity<List<PrivilegeDto>> getAllPrivileges() {

		List<PrivilegeDto> privileges = adminService.getAllPrivileges();
		if (privileges != null)
			return new ResponseEntity<List<PrivilegeDto>>(privileges,
					HttpStatus.OK);
		else
			return new ResponseEntity<List<PrivilegeDto>>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Method to create role. Though, we can develop a single method to update
	 * and create role.
	 * 
	 * @param roleDto
	 * @throws PrivilegeNotFoundException
	 * @throws RoleAlreadyExistsException
	 */
	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/roles", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createRole(@RequestBody RoleDto roleDto)
			throws PrivilegeNotFoundException, RoleAlreadyExistsException {
		Preconditions.checkNotNull(roleDto);
		adminService.addRole(roleDto);

	}

	/**
	 * If role doesn't exist, a role is created
	 * 
	 * @param roleDto
	 * @throws RoleNotFoundException
	 * @throws PrivilegeNotFoundException
	 * @throws RoleAlreadyExistsException
	 */
	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/roles/{role}/updateprivileges", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void updateRole(@RequestBody RoleDto roleDto)
			throws RoleNotFoundException, PrivilegeNotFoundException,
			RoleAlreadyExistsException {
		Preconditions.checkNotNull(roleDto);
		adminService.updatePrivilegesOfRole(roleDto);

	}

	/**
	 * Only rolename is updated
	 * 
	 * @param roleDto
	 * @throws RoleAlreadyExistsException
	 * @throws RoleNotFoundException
	 * @throws PrivilegeNotFoundException
	 */
	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/roles/{role}/update", method = RequestMethod.PUT)
	// request param as new rolename,
	@ResponseStatus(value = HttpStatus.OK)
	public void updateRoleName(
			@PathVariable(value = "role") String oldRoleName,
			@RequestParam(value = "name") String newRoleName)
			throws RoleAlreadyExistsException, RoleNotFoundException {
		Preconditions.checkArgument(!(oldRoleName == null || oldRoleName == ""
				|| newRoleName == null || newRoleName == ""),
				" Old / New Role name cannot be empty");
		;
		adminService.updateRoleName(oldRoleName, newRoleName);

	}

	/**
	 * This method cannot be used. As Author entity inherently used Role as
	 * child
	 * 
	 * @param roleName
	 * @throws RoleNotFoundException
	 * @throws RoleInUseException
	 */
	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/roles/{rolename}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteRole(@PathVariable(value = "rolename") String roleName)
			throws RoleNotFoundException, RoleInUseException {
		Preconditions.checkNotNull(roleName);
		adminService.deleteRole(roleName);

	}

	/**
	 * Should be called only from admin_app and By Admin
	 * 
	 * @return
	 * @throws RoleNotFoundException
	 *             when no roles are found
	 */
	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RoleDto>> getAllRoles()
			throws RoleNotFoundException {
		List<RoleDto> roleDtos = adminService.getAllRoles();
		if (roleDtos != null)
			return new ResponseEntity<List<RoleDto>>(roleDtos, HttpStatus.OK);
		else
			return new ResponseEntity<List<RoleDto>>(HttpStatus.NO_CONTENT);
	}

	/**
	 * This method is used to assign role.
	 * 
	 * @param userName
	 * @param roleName
	 * @throws UserNotFoundException
	 * @throws RoleNotFoundException
	 */
	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/authors/{username}", method = RequestMethod.PUT)
	// role as request param
	@ResponseStatus(value = HttpStatus.OK)
	public void assignRoleToAuthor(
			@PathVariable(value = "username", required = true) String userName,
			@RequestParam(value = "role", required = true) String roleName)
			throws UserNotFoundException, RoleNotFoundException {
		adminService.assignRoletoAuthor(userName, roleName);

	}
}
