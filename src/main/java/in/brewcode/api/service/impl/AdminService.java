package in.brewcode.api.service.impl;

import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.persistence.entity.Author;
import in.brewcode.api.persistence.entity.Privilege;
import in.brewcode.api.persistence.entity.Role;
import in.brewcode.api.service.IAdminService;
import in.brewcode.api.service.common.CommonService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@Service
@Transactional
public class AdminService extends CommonService implements IAdminService {

	public void createAuthor(AuthorDto authorDto) {
		if (Preconditions.checkNotNull(authorDto) != null) {
			Author author = convertAuthorDtoToEntity(authorDto);

			adminAuthorDao.save(author);

		}
	}

	public void deleteAuthor(AuthorDto authorDto) {

	}

	public void addNewRole(Role role) {
		// TODO Auto-generated method stub

	}

	public void addNewPrivilege(Privilege privilege) {
		// TODO Auto-generated method stub

	}

	public void deleteRole(Role role) {
		// TODO Auto-generated method stub

	}

	public void deletePrivilege(Privilege privilege) {
		// TODO Auto-generated method stub

	}

	public AuthorDto findAuthorById(Long id) {
		AuthorDto authorDto = null;
		Author author = adminAuthorDao.getById(id);

		Preconditions.checkNotNull(author);
		authorDto = convertToArticleAuthorDto(author);

		return authorDto;
	}
}
