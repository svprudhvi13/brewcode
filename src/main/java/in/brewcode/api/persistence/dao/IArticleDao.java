package in.brewcode.api.persistence.dao;

import in.brewcode.api.persistence.dao.common.IOperationsDao;
import in.brewcode.api.persistence.entity.Article;

import java.util.List;

public interface IArticleDao extends IOperationsDao<Article> {

	
	List<Article> getTopFiveArticles();
	List<Article> getAuthorArticles(Long authorId);
	public void submitForPublishing(Long editorId);
	 List<Article> getAllArticles();
	
}
