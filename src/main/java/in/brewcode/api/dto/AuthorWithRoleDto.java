package in.brewcode.api.dto;

public class AuthorWithRoleDto {

	@Override
	public String toString() {
		return "AuthorWithRoleDto [authorDto=" + authorDto + ", roleDto="
				+ roleDto + "]";
	}

	private AuthorDto authorDto;
	private RoleDto roleDto;
	private boolean isLocked;
	public AuthorDto getAuthorDto() {
		return authorDto;
	}

	public void setAuthorDto(AuthorDto authorDto) {
		this.authorDto = authorDto;
	}

	public RoleDto getRoleDto() {
		return roleDto;
	}

	public void setRoleDto(RoleDto roleDto) {
		this.roleDto = roleDto;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

}
