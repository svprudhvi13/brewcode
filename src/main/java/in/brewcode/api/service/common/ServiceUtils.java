package in.brewcode.api.service.common;

import java.util.HashSet;
import java.util.Set;

import in.brewcode.api.dto.ArticleDto;
import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.dto.ContentDto;
import in.brewcode.api.dto.PrivilegeDto;
import in.brewcode.api.dto.RoleDto;
import in.brewcode.api.persistence.entity.Article;
import in.brewcode.api.persistence.entity.Author;
import in.brewcode.api.persistence.entity.Content;
import in.brewcode.api.persistence.entity.Privilege;
import in.brewcode.api.persistence.entity.Role;

import com.google.common.base.Preconditions;

public class ServiceUtils {


	
	/**
	 * Content and Author Entities are not converted from Dto However, Author of
	 * article should never change and content entity are saved/updated first
	 * before updating article
	 * Last edited Date, Created Date and isActive fields are not converted to Entity as they
	 * are updated in Persistence layer with @Prepersist and @Preupdate
	 * @param articleDto
	 * @return
	 */
	public static Article converttoArticleEntity(ArticleDto articleDto, Article article) {
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



	public static Content convertToContentEntity(ContentDto contentDto, Content content) {
		
			Preconditions.checkArgument((contentDto!=null)&&(content!=null));
		
				content.setContentBody(contentDto.getContentDtoBody());
				//For new this will be 0, anyways.
				content.setContentId(contentDto.getContentDtoId());
				content.setContentMediaPath(contentDto.getContentDtoMediaPath());
			
			
		
		return content;
	}

	/**
	 * //This method is not required. Using this condition directly by accessing
	 * articleDao //and comparing with null. public static boolean
	 * checkIfArticleExists(long id){
	 * 
	 * 
	 * return articleDao.getById(id)!=null;
	 * 
	 * }
	 */
	public static ArticleDto convertToArticleDto(Article article) {
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

	public static ContentDto convertToContentDto(Content content) {
		ContentDto contentDto = null;
		if (Preconditions.checkNotNull(content != null)) {
			contentDto = new ContentDto();
			contentDto.setContentDtoBody(content.getContentBody());
			contentDto.setContentDtoId(content.getContentId());
			contentDto.setContentDtoLastEditedDate(content
					.getLastUpdatedDate());
			contentDto
					.setContentDtoCreatedDate(content.getCreateDate());
			contentDto.setContentDtoMediaPath(content.getContentMediaPath());
			contentDto.setIsActive(content.getIsActive());

		}
		return contentDto;
	}

	public static AuthorDto convertToArticleAuthorDto(Author articleAuthor) {
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

	public static Author convertToAuthorEntity(AuthorDto authorDto, Author author) {
		
		author.setAuthorUserName(authorDto.getAuthorUserName());
		author.setAuthorEmail(authorDto.getAuthorEmail());
		return author;
	}

	public static Role convertToRoleEntity(RoleDto roleDto, Role role) {
		Preconditions.checkArgument(roleDto != null && role != null);
	
		role.setRoleName(roleDto.getRoleName());
		return role;
	}

	public static RoleDto convertToRoleDto(Role role) {
		Preconditions.checkArgument(role != null);
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(role.getRoleName());
		if (role.getRolePrivileges() != null) {
			Set<PrivilegeDto> privilegeSet = new HashSet<PrivilegeDto>();
			for (Privilege priv : role.getRolePrivileges()) {
				privilegeSet.add(convertToPrivilegeDto(priv));
			}
		}

		return roleDto;
	}

	public static Privilege convertToPrivilegeEntity(PrivilegeDto privilegeDto,
			Privilege privilege) {
		privilege.setPrivilegeName(privilegeDto.getPrivilegeName());
		return privilege;
	}

	public static PrivilegeDto convertToPrivilegeDto(Privilege privilege) {
		Preconditions.checkArgument(privilege != null);
		PrivilegeDto privilegeDto = new PrivilegeDto();
		privilegeDto.setPrivilegeName(privilege.getPrivilegeName());
		return privilegeDto;
	}

}
