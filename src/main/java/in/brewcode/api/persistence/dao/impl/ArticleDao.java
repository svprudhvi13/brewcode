/**
 * 
 */
package in.brewcode.api.persistence.dao.impl;

import in.brewcode.api.persistence.dao.IArticleDao;
import in.brewcode.api.persistence.dao.common.GenericDao;
import in.brewcode.api.persistence.entity.Article;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ai
 * @param <T>
 *
 */
@Repository("articleDao")
public class ArticleDao extends GenericDao<Article> implements IArticleDao {

	public ArticleDao() {
		super();
		setClazz(Article.class);
	}

	public List<Article> getAllArticles() {
		List<Article> listArticles = null;

		listArticles = (List<Article>)entityManager.createQuery(
				"from " + Article.class.getName()).getResultList();
		return listArticles;

	}

	public List<Article> getAuthorArticles(Long authorId) {

		return null;
	}

	public void submitForPublishing(Long editorId) {

	}

	public List<Article> getTopFiveArticles() {
		String query = "From Article";

		List<Article> listArticles = entityManager.createQuery(query)
				.getResultList();

		return listArticles;
	}

}
