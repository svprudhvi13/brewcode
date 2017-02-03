package in.brewcode.api.web;

import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.dto.AuthorWithRoleDto;
import in.brewcode.api.dto.PrivilegeDto;
import in.brewcode.api.dto.RoleDto;
import in.brewcode.api.exception.PrivilegeAlreadyExistsException;
import in.brewcode.api.exception.PrivilegeNotFoundException;
import in.brewcode.api.exception.RoleAlreadyExistsException;
import in.brewcode.api.exception.RoleNotFoundException;
import in.brewcode.api.exception.UserAlreadyExistsException;
import in.brewcode.api.exception.UserNotFoundException;
import in.brewcode.api.service.IAdminService;
import in.brewcode.api.web.common.BaseController;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Preconditions;

@Controller(value = "adminController")
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {

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
	@RequestMapping(value = "/author/create", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public void createAuthor(@RequestBody AuthorDto authorDto,
			HttpServletResponse response) throws UserAlreadyExistsException {

		Preconditions.checkNotNull(authorDto);
		adminService.createAuthor(authorDto);
		logger.debug("Author created");

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
	@RequestMapping(value = "/author", method = RequestMethod.GET)
	@ResponseBody
	public AuthorDto findAuthorByUserName(
			@RequestParam(value = "username") final String username) {
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
	@RequestMapping(value = "/privilege/create", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createPrivilege(@RequestBody PrivilegeDto privilegeDto)
			throws PrivilegeAlreadyExistsException {
		Preconditions.checkNotNull(privilegeDto);

		adminService.addPrivilege(privilegeDto);

	}

	/**
	 * Method to create role. Though, we can develop a single method to update
	 * and create role.
	 * 
	 * @param roleDto
	 * @throws PrivilegeNotFoundException
	 */
	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/role/create", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createRole(@RequestBody RoleDto roleDto)
			throws PrivilegeNotFoundException {
		Preconditions.checkNotNull(roleDto);
		adminService.addRole(roleDto);

	}

	/**
	 * If role doesn't exist, a role is created
	 * 
	 * @param roleDto
	 * @throws RoleNotFoundException
	 * @throws PrivilegeNotFoundException
	 */
	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/role/updateprivileges", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void updateRole(@RequestBody RoleDto roleDto)
			throws RoleNotFoundException, PrivilegeNotFoundException {
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
	@RequestMapping(value = "/role/updaterolename", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void updateRoleName(String oldRoleName, String newRoleName)
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
	 */
	@PreAuthorize("#oauth2.hasScope('admin_app') and hasRole('ADMIN')")
	@RequestMapping(value = "/role/delete", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteRole(@RequestParam(value = "rolename") String roleName)
			throws RoleNotFoundException {
		Preconditions.checkNotNull(roleName);
		adminService.deleteRole(roleName);

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
	@RequestMapping(value = "/author/assignrole", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void assignRoleToAuthor(
			@RequestParam(value = "username", required = true) String userName,
			@RequestParam(value = "role", required = true) String roleName)
			throws UserNotFoundException, RoleNotFoundException {
		adminService.assignRoletoAuthor(userName, roleName);

	}
}