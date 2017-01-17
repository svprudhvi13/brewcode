package in.brewcode.api.dto;

import java.sql.Date;
import java.util.List;

public class ArticleDto {

	private long articleDtoId;

	
	private String articleName;

	
	private String articleDtoImageLocation;

	private Date articleDtoCreatedDate;

	private Date articleDtoLastEditedDate;

	private Date articleDtoPublishedDate;

	private char isActive;

	private AuthorDto articleAuthorDto;

	private List<ContentDto> articleContentDtos;

	public long getArticleDtoId() {
		return articleDtoId;
	}

	public void setArticleDtoId(long articleDtoId) {
		this.articleDtoId = articleDtoId;
	}

	public String getArticeName() {
		return articleName;
	}

	public void setArticeName(String articeName) {
		this.articleName = articeName;
	}

	public String getArticleDtoImageLocation() {
		return articleDtoImageLocation;
	}

	public void setArticleDtoImageLocation(String articleDtoImageLocation) {
		this.articleDtoImageLocation = articleDtoImageLocation;
	}

	public Date getArticleDtoCreatedDate() {
		return articleDtoCreatedDate;
	}

	public void setArticleDtoCreatedDate(Date articleDtoCreatedDate) {
		this.articleDtoCreatedDate = articleDtoCreatedDate;
	}

	public Date getArticleDtoLastEditedDate() {
		return articleDtoLastEditedDate;
	}

	public void setArticleDtoLastEditedDate(Date articleDtoLastEditedDate) {
		this.articleDtoLastEditedDate = articleDtoLastEditedDate;
	}

	public Date getArticleDtoPublishedDate() {
		return articleDtoPublishedDate;
	}

	public void setArticleDtoPublishedDate(Date articleDtoPublishedDate) {
		this.articleDtoPublishedDate = articleDtoPublishedDate;
	}

	public char getIsActive() {
		return isActive;
	}

	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}

	public AuthorDto getArticleAuthorDto() {
		return articleAuthorDto;
	}

	public void setArticleAuthorDto(AuthorDto articleAuthorDto) {
		this.articleAuthorDto = articleAuthorDto;
	}

	public List<ContentDto> getArticleContentDtos() {
		return articleContentDtos;
	}

	public void setArticleContentDtos(List<ContentDto> articleContentDtos) {
		this.articleContentDtos = articleContentDtos;
	}

	
}
