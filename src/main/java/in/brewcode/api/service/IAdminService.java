package in.brewcode.api.service;

import java.util.List;

import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.persistence.entity.Privilege;
import in.brewcode.api.persistence.entity.Role;

public interface IAdminService {
	public AuthorDto findByUserName(String userName);
	public void createAuthor(AuthorDto authorDto);
	public void updateAuthor(AuthorDto authorDto);
	public void deleteAuthor(String authorUserName);

	public void addNewRole(Role role);

	public void addNewPrivilege(Privilege privilege);

	public void deleteRole(Role role);

	public void deletePrivilege(Privilege privilege);
	
	public List<AuthorDto> findAllAuthors();
}
