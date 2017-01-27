package in.brewcode.api.web;

import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.dto.AuthorWithRoleDto;
import in.brewcode.api.dto.PrivilegeDto;
import in.brewcode.api.dto.RoleDto;
import in.brewcode.api.exception.PrivilegeAlreadyExistsException;
import in.brewcode.api.exception.PrivilegeNotFoundException;
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
	@RequestMapping(value = "/author", method = RequestMethod.GET)
	@ResponseBody
	public AuthorDto findAuthorByUserName(
			@RequestParam(value = "username") final String username) {
		AuthorDto authorDto = null;
		authorDto = adminService.findByUserName(username);
		return authorDto;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/authors", method = RequestMethod.GET)
	@ResponseBody
	public List<AuthorWithRoleDto> findAllAuthors() {
		List<AuthorWithRoleDto> listAuthors = null;

		listAuthors = adminService.findAllAuthors();

		return listAuthors;

	}

	@RequestMapping(value = "/privilege/create", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createPrivilege(@RequestBody PrivilegeDto privilegeDto) {
		Preconditions.checkNotNull(privilegeDto);
		try {
			adminService.addPrivilege(privilegeDto);
		} catch (PrivilegeAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/role/create", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createRole(@RequestBody RoleDto roleDto) {
		Preconditions.checkNotNull(roleDto);
		try {
			adminService.addRole(roleDto);
		} catch (PrivilegeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
	}

	@RequestMapping(value = "/role/delete", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteRole(@RequestParam(value = "rolename") String roleName) {
		Preconditions.checkNotNull(roleName);
		try {
			adminService.deleteRole(roleName);
		} catch (RoleNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/author/assignrole", method = RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.CREATED)
	public void assignRoleToAuthor(
			@RequestParam(value = "username", required = true) String userName,
			@RequestParam(value = "role", required = true) String roleName) {
try {
	adminService.assignRoletoAuthor(userName, roleName);
} catch (UserNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (RoleNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		
	}
}