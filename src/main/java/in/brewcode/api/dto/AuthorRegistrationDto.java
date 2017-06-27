package in.brewcode.api.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuthorRegistrationDto {
	@Override
	public String toString() {
		return "AuthorRegistrationDto [adminFirstName=" + adminFirstName
				+ ", adminLastName=" + adminLastName + ", adminDateOfBirth="
				+ adminDateOfBirth + ", adminCreatedDate=" + adminCreatedDate
				+ ", address=" + address + ", authorLoginDto=" + authorLoginDto
				+ "]";
	}

	@NotNull
	private String adminFirstName;
	@NotNull
	private String adminLastName;
	@NotNull
	@org.codehaus.jackson.annotate.JsonIgnore
	private LocalDate adminDateOfBirth;
	
	@JsonIgnore
	@org.codehaus.jackson.annotate.JsonIgnore
	private LocalDateTime adminCreatedDate;
	
	private String address;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	private AuthorLoginDto authorLoginDto;
	@NotNull
	private String confirmPassword;

	public LocalDate getAdminDateOfBirth() {
		return adminDateOfBirth;
	}

	public void setAdminDateOfBirth(LocalDate localDate) {
		this.adminDateOfBirth = localDate;
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
	

	public LocalDateTime getAdminCreatedDate() {
		return adminCreatedDate;
	}

	public void setAdminCreatedDate(LocalDateTime adminCreatedDate) {
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
