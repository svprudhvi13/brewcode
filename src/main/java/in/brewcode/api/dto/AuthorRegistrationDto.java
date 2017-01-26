package in.brewcode.api.dto;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuthorRegistrationDto extends AuthorLoginDto {
	@NotNull
	private String adminFirstName;
	@NotNull
	private String adminLastName;
	@NotNull
	private Date adminDateOfBirth;
	
	private Date adminCreatedDate;
	
	private Date adminExpiryDate;
	
	private AuthorLoginDto authorLoginDto;
	@NotNull
	@JsonIgnore
	@org.codehaus.jackson.annotate.JsonIgnore
	private String confirmPassword;

	public Date getAdminDateOfBirth() {
		return adminDateOfBirth;
	}

	public void setAdminDateOfBirth(Date adminDateOfBirth) {
		this.adminDateOfBirth = adminDateOfBirth;
	}



	public String getAdminFirstName() {
		return adminFirstName;
	}

	public void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}

	public String getAdminLastName() {
		return adminLastName;
	}

	public void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
	}

	/*
	 * * All Details of this class, along with adminName ie., unique user id of
	 * super class is show by this method
	 */
	

	public Date getAdminExpiryDate() {
		return adminExpiryDate;
	}

	public void setAdminExpiryDate(Date adminExpiryDate) {
		this.adminExpiryDate = adminExpiryDate;
	}

	public Date getAdminCreatedDate() {
		return adminCreatedDate;
	}

	public void setAdminCreatedDate(Date adminCreatedDate) {
		this.adminCreatedDate = adminCreatedDate;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public AuthorLoginDto getAuthorLoginDto() {
		return authorLoginDto;
	}

	public void setAuthorLoginDto(AuthorLoginDto authorLoginDto) {
		this.authorLoginDto = authorLoginDto;
	}

}
