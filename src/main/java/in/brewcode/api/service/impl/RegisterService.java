package in.brewcode.api.service.impl;

public interface RegisterService {

	public boolean checkUserNameAvailability(String userName);
	public boolean saveUserDetails();
	
}
