package in.brewcode.api.dto;

import java.sql.Date;

public class AuthorRegistrationDto extends AuthorLoginDto {
	private String adminFirstName;
	private String adminLastName;
	private Date adminDateOfBirth;
	private Date adminCreatedDate;
	private Date adminExpiryDate;
	private String adminEmail;

	public Date getAdminDateOfBirth() {
		return adminDateOfBirth;
	}

	public void setAdminDateOfBirth(Date adminDateOfBirth) {
		this.adminDateOfBirth = adminDateOfBirth;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
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
	@Override
	public String toString() {
		return "BrewcodeAdminDetails [adminFirstName=" + adminFirstName
				+ ", adminLastName=" + adminLastName + ", adminDateOfBirth="
				+ adminDateOfBirth + ", adminEmail=" + adminEmail
				+  "]";
	}

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

}
