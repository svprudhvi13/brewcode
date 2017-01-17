package in.brewcode.api.service.common;

import in.brewcode.api.dto.ArticleDto;
import in.brewcode.api.dto.ContentDto;
import in.brewcode.api.persistence.entity.Article;
import in.brewcode.api.persistence.entity.Content;

import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@Transactional
public  class CommonService extends AuthorEntityConvertor{
	

	
	/**
	 * Content and Author Entities are not converted from Dto However, Author of
	 * article should never change and content entity are saved/updated first
	 * before updating article
	 * Last edited Date, Created Date and isActive fields are not converted to Entity as they
	 * are updated in Persistence layer with @Prepersist and @Preupdate
	 * @param articleDto
	 * @return
	 */
	protected Article converttoArticleEntity(ArticleDto articleDto, Article article) {
			Preconditions.checkArgument((articleDto!=null)&&article!=null);
						
			article.setArticleName(articleDto.getArticleName());
			
			article.setArticlePublishedDate(articleDto
					.getArticleDtoPublishedDate());
			article.setIsActive(articleDto.getIsActive());

		/**
		 * 	// Code to full convert of ContentDto to Content (Entity)
		 
			if (Preconditions
					.checkNotNull(articleDto.getArticleContentDtos() != null)) {
				List<Content> articleContents = new ArrayList<Content>();
				for (ContentDto contentDto : articleDto.getArticleContentDtos()) {
					articleContents.add(convertToContentEntity(contentDto));
				}
			}
*/
			return article;
		}



	protected Content convertToContentEntity(ContentDto contentDto, Content content) {
		
			Preconditions.checkArgument((contentDto!=null)&&(content!=null));
		
				content.setContentBody(contentDto.getContentDtoBody());
				//For new this will be 0, anyways.
				content.setContentId(contentDto.getContentDtoId());
				content.setContentMediaPath(contentDto.getContentDtoMediaPath());
			
			
		
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
			articleDto.setArticleName(article.getArticleName());
			articleDto.setArticleDtoPublishedDate(article
					.getArticlePublishedDate());
			articleDto
					.setArticleDtoCreatedDate(article.getArticleCreatedDate());
			articleDto.setArticleDtoId(article.getArticleId());
			articleDto.setArticleDtoLastEditedDate(article
					.getArticleLastEditedDate());
			articleDto.setIsActive(article.getIsActive());

			/*
			 * //commented to avoid eager fetching
			 * articleDto.setArticleAuthorDto(convertToArticleAuthorDto(article
			 * .getArticleAuthor()));
			 * 
			 * if (Preconditions .checkNotNull(article.getArticleContents() !=
			 * null)) { List<ContentDto> articleContentDtos = new
			 * ArrayList<ContentDto>(); for (Content content :
			 * article.getArticleContents()) {
			 * articleContentDtos.add(convertToContentDto(content)); }
			 * articleDto.setArticleContentDtos(articleContentDtos); }
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



}
