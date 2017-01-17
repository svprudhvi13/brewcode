package in.brewcode.api.service;

import in.brewcode.api.dto.ArticleDto;
import in.brewcode.api.dto.ContentDto;

import java.util.List;

public interface IArticleAndContentService {
	void saveOrUpdateArticle(ArticleDto articleDto);
	void saveOrUpdateContent(ContentDto contentDto);
	void deleteContent(long id);
	void deleteArticle(long id);
	public ArticleDto getFullArticleById(long id);
	List<ArticleDto> getAllArticles();
	List<ArticleDto> getTopArticles();
	ContentDto getContentById(long id);
	
}
