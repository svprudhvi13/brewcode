package in.brewcode.api.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import in.brewcode.api.persistence.dao.IAdminAuthorDao;
import in.brewcode.api.persistence.dao.common.GenericDao;
import in.brewcode.api.persistence.entity.Author;
import in.brewcode.api.persistence.entity.Content;

@Repository(value="adminAuthorDao")
public class AdminAuthorDao extends GenericDao<Author> implements IAdminAuthorDao{
	
	public AdminAuthorDao() {
	
	super();
	setClazz(Author.class);
	}

	
}
