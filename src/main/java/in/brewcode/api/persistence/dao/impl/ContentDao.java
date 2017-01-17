package in.brewcode.api.persistence.dao.impl;

import in.brewcode.api.persistence.dao.IContentDao;
import in.brewcode.api.persistence.dao.common.GenericDao;
import in.brewcode.api.persistence.entity.Content;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository(value="contentDao")
public class ContentDao extends GenericDao<Content> implements IContentDao{

	public ContentDao(){
		super();
		setClazz(Content.class);
	}

	

	public List<Content> getArticleContents(Long id) {
		List<Content> listContents = null;
		String queryString = 
				"FROM Content c where c.article.articleId=:aId";
		
		listContents=(List<Content>)entityManager.createQuery(queryString, Content.class).setParameter("aId",id).getResultList();
		
		return listContents; }



}
