package in.brewcode.api.dto;


public class AuthorLoginDto extends AuthorDto {
	
	
	
	private String adminPassword;

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	// Shalln't retreive Password
	/*
	 * public String getAdminPassword() { return adminPassword; }
	 */

}
