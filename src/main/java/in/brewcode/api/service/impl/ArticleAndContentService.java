package in.brewcode.api.service.impl;

import static in.brewcode.api.service.common.ServiceUtils.convertToArticleDto;
import static in.brewcode.api.service.common.ServiceUtils.convertToContentDto;
import static in.brewcode.api.service.common.ServiceUtils.convertToContentEntity;
import static in.brewcode.api.service.common.ServiceUtils.converttoArticleEntity;
import in.brewcode.api.dto.ArticleDto;
import in.brewcode.api.dto.ContentDto;
import in.brewcode.api.exception.ArticleNotFoundException;
import in.brewcode.api.persistence.dao.IAdminAuthorDao;
import in.brewcode.api.persistence.dao.IArticleDao;
import in.brewcode.api.persistence.dao.IContentDao;
import in.brewcode.api.persistence.entity.Article;
import in.brewcode.api.persistence.entity.Author;
import in.brewcode.api.persistence.entity.Content;
import in.brewcode.api.service.IArticleAndContentService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@Service(value = "articleAndContentService")
@Transactional
public class ArticleAndContentService implements IArticleAndContentService {

	private static Logger logger = Logger
			.getLogger(ArticleAndContentService.class);

	@Autowired
	private IAdminAuthorDao adminAuthorDao;

	@Autowired
	private IArticleDao articleDao;

	@Autowired
	private IContentDao contentDao;

	protected PagingAndSortingRepository<Content, Long> getContentDao() {
		return contentDao;
	}

	protected PagingAndSortingRepository<Article, Long> getArticleDao() {
		return articleDao;
	}

	protected PagingAndSortingRepository<Author, Long> getAdminAuthorDao() {
		return adminAuthorDao;
	}

	public ContentDto getContentById(long id) {
		ContentDto contentDto = null;
		contentDto = convertToContentDto(contentDao.findOne(id));

		return contentDto;
	}

	public List<ArticleDto> getTopArticles() {

		List<ArticleDto> listArticleDto = null;
		// logic

		return listArticleDto;
	}

	public List<ArticleDto> getAllArticles() {
		List<ArticleDto> listArticleDto = null;
		List<Article> listArticles = articleDao.findAll();
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
	public void updateArticle(ArticleDto articleDto) {

		Article article = getArticleDao().findOne(articleDto.getArticleDtoId());

		if (Preconditions.checkNotNull(article != null)) {
			articleDao.save(converttoArticleEntity(articleDto, article));
		}
	}

	/**
	 * Write ArticleDto Validation using validators at Controller level
	 * Conditions: Required fields of ArticleDto are: Author, Article name.
	 * (non-Javadoc)
	 * 
	 * @see in.brewcode.api.service.IArticleAndContentService#saveArticle(in.brewcode.api.dto.ArticleDto)
	 */
	public void saveArticle(ArticleDto articleDto) {
		Article article = converttoArticleEntity(articleDto, new Article());

		Author author = adminAuthorDao.findByAuthorUserName(articleDto
				.getArticleAuthorDto().getAuthorUserName());
		article.setArticleAuthor(author);
		if (articleDto.getArticleContentDtos() != null) {
			List<Content> articleContents = new ArrayList<Content>();
			for (ContentDto contentDto : articleDto.getArticleContentDtos()) {
				Content content = new Content();
				content.setArticle(article);
				articleContents
						.add(convertToContentEntity(contentDto, content));
			}
		}

		articleDao.save(article);

	}

	/**
	 * Validate contentDto and articleId(whether such article exist) at
	 * Controller level. ArticleId is required to avoid improper cascading.
	 * 
	 */
	public void saveContent(ContentDto contentDto, Long articleId) {

		Article article = articleDao.findOne(articleId);
		Preconditions.checkNotNull(article);
		Content content = convertToContentEntity(contentDto, new Content());
		content.setArticle(article);
		contentDao.save(content);
	}

	public void updateContent(ContentDto contentDto) {
		Content content = getContentDao().findOne(contentDto.getContentDtoId());
		if (Preconditions.checkNotNull(content != null)) {
			contentDao.save(convertToContentEntity(contentDto, content));
		}

	}

	public void deleteContent(long id) {

	}

	public void deleteArticle(long id) {
		// TODO Auto-generated method stub

	}

	public ArticleDto getFullArticleById(long id)
			throws ArticleNotFoundException {
		
		Article article = articleDao.findOne(id);
		if (article == null) {
			throw new ArticleNotFoundException(
					"Article name or id doesn't exist");
		} else {

			Hibernate.initialize(article.getArticleContents());
			logger.info(Hibernate.isInitialized(article.getArticleContents())
					+ " **Status of initialize");

			return convertToArticleDto(article);

		}

	}

}
