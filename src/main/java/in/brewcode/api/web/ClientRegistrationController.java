package in.brewcode.api.web;

import java.util.List;

import in.brewcode.api.web.common.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Preconditions;

@RequestMapping(value="/client")
public class ClientRegistrationController extends BaseController{

	@Autowired
	private ClientDetailsService clientDetailsService;
	
	@Autowired
	private ClientRegistrationService clientRegistrationService;
	
	@PreAuthorize("#oauth2.hasScope('register_client')")
	@RequestMapping(value="/register", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public void registerClient(BaseClientDetails clientDetails){
		
		Preconditions.checkNotNull(clientDetails);
		
		clientRegistrationService.addClientDetails(clientDetails);
	}

	@PreAuthorize("#oauth2.hasScope('brewcode')")
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseStatus(value=HttpStatus.OK)
	public void updateClient(ClientDetails clientDetails){
		
		Preconditions.checkNotNull(clientDetails);
		clientRegistrationService.addClientDetails(clientDetails);
	}
	
	@PreAuthorize("#oauth2.hasScope('client, admin') or hasRole('ADMIN')")
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	@ResponseStatus(value=HttpStatus.OK)
	public void removeClient(String clientId){
		
		Preconditions.checkNotNull(clientId);
		clientRegistrationService.removeClientDetails(clientId);
	}

	@PreAuthorize("#oauth2.hasScope('admin') and hasRole('ADMIN')")
	@RequestMapping(value="getAllClients", method=RequestMethod.GET)
	public List<ClientDetails> getAllClients(){
		
		return clientRegistrationService.listClientDetails();
	}
}
