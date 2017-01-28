package in.brewcode.api.service;

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

import java.util.List;

public interface IAdminService {
	public AuthorDto findByUserName(String userName);

	public void lockAuthor(String userName) throws UserNotFoundException;

	public void unlockAuthor(String userName) throws UserNotFoundException;

	public void createAuthor(AuthorDto authorDto)
			throws UserAlreadyExistsException;

	//public void updateAuthor(AuthorDto authorDto);

	public void deleteAuthor(String authorUserName) throws UserNotFoundException;

	public void assignRoletoAuthor(String authorUserName, String roleName) throws UserNotFoundException,  in.brewcode.api.exception.RoleNotFoundException;

	public void removeRoleOfAuthor(String authorUserName, String roleName) throws UserNotFoundException, in.brewcode.api.exception.RoleNotFoundException;

	public void addRole(RoleDto role) throws PrivilegeNotFoundException;

	public void addPrivilege(PrivilegeDto privilege) throws PrivilegeAlreadyExistsException;
	
	public void updatePrivilegesOfRole(RoleDto roleDto) throws RoleNotFoundException, PrivilegeNotFoundException;
//	public void updatePrivilege(PrivilegeDto privilege);

	public void updateRoleName(String oldRoleName, String newRoleName ) throws RoleAlreadyExistsException, RoleNotFoundException;

	public void deleteRole(String roleName) throws RoleNotFoundException;

	public void deletePrivilege(String privilegeName) throws PrivilegeNotFoundException;

	public List<AuthorWithRoleDto> findAllAuthors();
}
