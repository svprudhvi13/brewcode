package in.brewcode.api.persistence.dao;

import in.brewcode.api.persistence.entity.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IArticleDao extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

	/*
	List<Article> getTopFiveArticles();
	List<Article> getAuthorArticles(Long authorId);
	public void submitForPublishing(Long editorId);
	 List<Article> getAllArticles();
	*/
}
