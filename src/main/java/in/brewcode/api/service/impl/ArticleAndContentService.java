package in.brewcode.api.service.impl;

import in.brewcode.api.dto.ArticleDto;
import in.brewcode.api.dto.ContentDto;
import in.brewcode.api.persistence.entity.Article;
import in.brewcode.api.persistence.entity.Content;
import in.brewcode.api.service.IArticleAndContentService;
import in.brewcode.api.service.common.CommonService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@Service(value = "articleAndContentService")
@Transactional
public class ArticleAndContentService extends CommonService implements IArticleAndContentService {

	private static Logger logger = Logger
			.getLogger(ArticleAndContentService.class);
	
	public ContentDto getContentById(long id) {
		ContentDto contentDto = null;
		contentDto = convertToContentDto(contentDao.getById(id));

		return contentDto;
	}

	


	public List<ArticleDto> getTopArticles() {

		List<ArticleDto> listArticleDto = null;
		// logic

		return listArticleDto;
	}

	public List<ArticleDto> getAllArticles() {
		List<ArticleDto> listArticleDto = null;
		List<Article> listArticles = articleDao.getAllArticles();
		if (Preconditions.checkNotNull(listArticles != null)) {
			listArticleDto = new ArrayList<ArticleDto>();
			for (Article article : listArticles) {
				logger.debug("Converting" + article.getArticleId()
						+ " articleentity to content");
				listArticleDto.add(convertToArticleDto(article));

			}
		}
		return listArticleDto;
	}

	/**
	 * 
	 * Full update on Article is not performed(Related members are ignored).
	 * From front end unsaved ContentDto to sent to server and after successful
	 * persistence of all Content Entities this method is called
	 * 
	 * @param articleDto
	 * 
	 */
	public void saveOrUpdateArticle(ArticleDto articleDto) {
		if (Preconditions.checkNotNull(articleDto) != null) {

			articleDao.update(converttoArticleEntity(articleDto));
		}
	}

	public void saveOrUpdateContent(ContentDto contentDto) {
		if (Preconditions.checkNotNull(contentDto) != null) {
			contentDao.update(convertToContentEntity(contentDto));
		}
	}

	public void deleteContent(long id) {
		// Todo logic
	}

	public void deleteArticle(long id) {
		// TODO Auto-generated method stub

	}

	public ArticleDto getFullArticleById(long id) {
		ArticleDto articleDto = null;
		Article article = articleDao.getById(id);

		Hibernate.initialize(article.getArticleContents());
		System.out
				.println(Hibernate.isInitialized(article.getArticleContents())
						+ " **Status of initialize");
		if (Preconditions.checkNotNull(article != null)) {
			for (Content c : article.getArticleContents()) {
				System.out.println(c.toString() + "*** content");
			}

			articleDto = convertToArticleDto(article);
			
			
		}
		return articleDto;
	}

}
