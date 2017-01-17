package in.brewcode.api.service;

import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.persistence.entity.Privilege;
import in.brewcode.api.persistence.entity.Role;

public interface IAdminService {

	public void createAuthor(AuthorDto authorDto);

	public void deleteAuthor(AuthorDto authorDto);

	public void addNewRole(Role role);

	public void addNewPrivilege(Privilege privilege);

	public void deleteRole(Role role);

	public void deletePrivilege(Privilege privilege);
	
	public AuthorDto findAuthorById(Long id);
}
