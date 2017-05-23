package in.brewcode.api.service;

import in.brewcode.api.dto.ArticleDto;
import in.brewcode.api.dto.ContentDto;
import in.brewcode.api.exception.ArticleNotFoundException;

import java.util.List;

public interface IArticleAndContentService {
	void updateArticle(ArticleDto articleDto);
	void updateContent(ContentDto contentDto);
	void saveArticle(ArticleDto articleDto);
	void saveContent(ContentDto contentDto, Long ArticleId);
	void deleteContent(long id);
	void deleteArticle(long id);
	public ArticleDto getFullArticleById(long id) throws ArticleNotFoundException;
	List<ArticleDto> getAllArticles();
	List<ArticleDto> getTopArticles();
	ContentDto getContentById(long id);
	
}
