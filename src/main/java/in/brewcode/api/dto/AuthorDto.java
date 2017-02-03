package in.brewcode.api.dto;

import javax.validation.constraints.NotNull;

public class AuthorDto {

	private String authorUserName;

	private String authorEmail;

	@NotNull
	//@JsonIgnore
	//@org.codehaus.jackson.annotate.JsonIgnore
	public String getAuthorUserName() {
		return authorUserName;
	}

	public void setAuthorUserName(String authorUserName) {
		this.authorUserName = authorUserName;
	}

	@NotNull
	//@JsonIgnore
	//@org.codehaus.jackson.annotate.JsonIgnore
	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

}
