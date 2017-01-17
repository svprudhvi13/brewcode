package in.brewcode.api.service.common;

import in.brewcode.api.dto.ArticleDto;
import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.dto.ContentDto;
import in.brewcode.api.persistence.dao.IAdminAuthorDao;
import in.brewcode.api.persistence.dao.IArticleDao;
import in.brewcode.api.persistence.dao.IContentDao;
import in.brewcode.api.persistence.entity.Article;
import in.brewcode.api.persistence.entity.Author;
import in.brewcode.api.persistence.entity.Content;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;

public class CommonService {
	@Autowired
	protected IAdminAuthorDao adminAuthorDao;

	/*
	 * @Autowired protected IRoleDao roleDao;
	 * 
	 * @Autowired protected IPrivilegeDao privilegeDao;
	 */

	@Autowired
	protected IArticleDao articleDao;

	@Autowired
	protected IContentDao contentDao;

	/**
	 * Content and Author Entites are not converted from Dto However, Author of
	 * article should never change and content entity are saved/updated first
	 * before updating article
	 * 
	 * @param articleDto
	 * @return
	 */
	protected Article converttoArticleEntity(ArticleDto articleDto) {
		Article article = null;
		if (Preconditions.checkNotNull(articleDto != null)) {
			long articleId = articleDto.getArticleDtoId();

			if (articleId != 0) {
				article = articleDao.getById(articleId);
				if (article == null)
					article = new Article();
			}

			article.setArticeName(articleDto.getArticeName());
			article.setArticlePublishedDate(articleDto
					.getArticleDtoPublishedDate());
			article.setArticleCreatedDate(articleDto.getArticleDtoCreatedDate());
			article.setArticleLastEditedDate(articleDto
					.getArticleDtoLastEditedDate());
			article.setIsActive(articleDto.getIsActive());

			// Code to full convert of ContentDto to Content (Entity)
			if (Preconditions
					.checkNotNull(articleDto.getArticleContentDtos() != null)) {
				List<Content> articleContents = new ArrayList<Content>();
				for (ContentDto contentDto : articleDto.getArticleContentDtos()) {
					articleContents.add(convertToContentEntity(contentDto));
				}
			}

		}

		return article;
	}

	protected Content convertToContentEntity(ContentDto contentDto) {
		Content content = null;
		if (Preconditions.checkNotNull(contentDto != null)) {
			Article article = articleDao.getById(contentDto.getArticleDtoId());
			if (Preconditions.checkNotNull(article != null)) {

				// Shouldnt directly create content entity, check for existing
				// content with that id i
				// If content doesn't exists, create new Content().
				// Else fetch and use setters
				content = contentDao.getById(contentDto.getContentDtoId());
				if (content == null) {
					content = new Content();
					content.setArticle(article);

				}
				content.setContentBody(contentDto.getContentDtoBody());
				content.setContentCreatedDate(contentDto
						.getContentDtoCreatedDate());
				content.setContentId(contentDto.getContentDtoId());
				content.setContentLastEditedDate(contentDto
						.getContentDtoLastEditedDate());
				content.setContentMediaPath(contentDto.getContentDtoMediaPath());
				content.setIsActive(contentDto.getIsActive());

			}
		}
		return content;
	}

	/**
	 * //This method is not required. Using this condition directly by accessing
	 * articleDao //and comparing with null. protected boolean
	 * checkIfArticleExists(long id){
	 * 
	 * 
	 * return articleDao.getById(id)!=null;
	 * 
	 * }
	 */
	protected ArticleDto convertToArticleDto(Article article) {
		ArticleDto articleDto = null;
		if (Preconditions.checkNotNull(article != null)) {
			articleDto = new ArticleDto();
			articleDto.setArticeName(article.getArticeName());
			articleDto.setArticleDtoPublishedDate(article
					.getArticlePublishedDate());
			articleDto
					.setArticleDtoCreatedDate(article.getArticleCreatedDate());
			articleDto.setArticleDtoId(article.getArticleId());
			articleDto.setArticleDtoLastEditedDate(article
					.getArticleLastEditedDate());
			articleDto.setIsActive(article.getIsActive());
			
			/*//commented to avoid eager fetching
			articleDto.setArticleAuthorDto(convertToArticleAuthorDto(article
					.getArticleAuthor()));

			if (Preconditions
					.checkNotNull(article.getArticleContents() != null)) {
				List<ContentDto> articleContentDtos = new ArrayList<ContentDto>();
				for (Content content : article.getArticleContents()) {
					articleContentDtos.add(convertToContentDto(content));
				}
				articleDto.setArticleContentDtos(articleContentDtos);
			}
*/
		}
		return articleDto;
	}

	protected ContentDto convertToContentDto(Content content) {
		ContentDto contentDto = null;
		if (Preconditions.checkNotNull(content != null)) {
			contentDto = new ContentDto();
			contentDto.setContentDtoBody(content.getContentBody());
			contentDto.setContentDtoId(content.getContentId());
			contentDto.setContentDtoLastEditedDate(content
					.getContentLastEditedDate());
			contentDto
					.setContentDtoCreatedDate(content.getContentCreatedDate());
			contentDto.setContentDtoMediaPath(content.getContentMediaPath());
			contentDto.setIsActive(content.getIsActive());

		}
		return contentDto;
	}

	protected AuthorDto convertToArticleAuthorDto(Author articleAuthor) {
		AuthorDto authorDto = null;
		/*
		 * Only author user name is set here
		 */
		if (Preconditions.checkNotNull(articleAuthor != null)) {
			authorDto = new AuthorDto();
			authorDto.setAuthorUserName(articleAuthor.getAuthorUserName());
			authorDto.setAuthorEmail(articleAuthor.getAuthorEmail());
		}

		return authorDto;
	}
	
	protected Author convertAuthorDtoToEntity(AuthorDto authorDto) {

		Author author = new Author();
		author.setAuthorUserName(authorDto.getAuthorUserName());
		author.setAuthorEmail(authorDto.getAuthorEmail());
		return author;
	}

}
