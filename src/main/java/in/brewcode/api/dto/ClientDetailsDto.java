package in.brewcode.api.dto;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;


public class ClientDetailsDto implements ClientDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 166231454412930941L;

	public String getClientId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> getResourceIds() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isSecretRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getClientSecret() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isScoped() {
		// TODO Auto-generated method stub
		return false;
	}

	public Set<String> getScope() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> getAuthorizedGrantTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> getRegisteredRedirectUri() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getAccessTokenValiditySeconds() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getRefreshTokenValiditySeconds() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAutoApprove(String scope) {
		// TODO Auto-generated method stub
		return false;
	}

	public Map<String, Object> getAdditionalInformation() {
		// TODO Auto-generated method stub
		return null;
	}

}
