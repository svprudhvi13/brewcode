package in.brewcode.api.service.impl;

import in.brewcode.api.dto.ArticleDto;
import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.persistence.dao.IAdminAuthorDao;
import in.brewcode.api.persistence.entity.Article;
import in.brewcode.api.persistence.entity.Author;
import in.brewcode.api.persistence.entity.Privilege;
import in.brewcode.api.persistence.entity.Role;
import in.brewcode.api.service.IAdminService;
import in.brewcode.api.service.common.AuthorEntityConvertor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@Service
@Transactional
public class AdminService extends AuthorEntityConvertor implements
		IAdminService {
	@Autowired
	private IAdminAuthorDao adminAuthorDao;

	
	protected PagingAndSortingRepository<Author, Long> getAdminAuthorDao() {

		return adminAuthorDao;
	}
	


	public void createAuthor(AuthorDto authorDto) {
		if (Preconditions.checkNotNull(authorDto) != null) {

			Author author = convertToAuthorEntity(authorDto, new Author());

			getAdminAuthorDao().save(author);

		}
	}

	public void deleteAuthor(AuthorDto authorDto) {
		// TODO Auto-generated method stub
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
		Author author = getAdminAuthorDao().findOne(id);

		Preconditions.checkNotNull(author);
		authorDto = convertToArticleAuthorDto(author);

		return authorDto;
	}

	public void updateAuthor(AuthorDto authorDto) {
		Author author = getAdminAuthorDao().findOne(authorDto.getAuthorId());
		getAdminAuthorDao().save(convertToAuthorEntity(authorDto, author));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.brewcode.api.service.IAdminService#findAllAuthors()
	 */
	@SuppressWarnings("unchecked")
	public List<AuthorDto> findAllAuthors() {
		List<AuthorDto> listAuthorDtos = null;
		Iterator<Author> listAuthors = (Iterator<Author>) getAdminAuthorDao().findAll();
		if (listAuthors != null) {
			listAuthorDtos = new ArrayList<AuthorDto>();
			while(listAuthors.hasNext()) {
				listAuthorDtos.add(convertToArticleAuthorDto(listAuthors.next()));
			}
		}
		return listAuthorDtos;
	}



	public AuthorDto findByUserName(String userName) {
		
		Author author = adminAuthorDao.findByAuthorUserName(userName);
		return convertToArticleAuthorDto(author);
	}

}
