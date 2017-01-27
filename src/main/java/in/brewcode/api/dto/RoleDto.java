package in.brewcode.api.dto;

import java.util.Set;

public class RoleDto {
	
	
	private String roleName;
	
	private Set<PrivilegeDto> privileges;

	public Set<PrivilegeDto> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<PrivilegeDto> privileges) {
		this.privileges = privileges;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
}
