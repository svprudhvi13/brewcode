package in.brewcode.api.dto;

public class PrivilegeDto {

	@Override
	public String toString() {
		return "PrivilegeDto [privilegeName=" + privilegeName + "]";
	}

	private String privilegeName;

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
}
