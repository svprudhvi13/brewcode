package in.brewcode.api.service.common;

import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.persistence.entity.Author;

import com.google.common.base.Preconditions;

public class AuthorEntityConvertor {

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

	protected Author convertToAuthorEntity(AuthorDto authorDto, Author author) {

		author.setAuthorUserName(authorDto.getAuthorUserName());
		author.setAuthorEmail(authorDto.getAuthorEmail());
		return author;
	}
}
