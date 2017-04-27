package in.brewcode.api.auth.server.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends
		AuthorizationServerConfigurerAdapter {
	/**
	 * This bean is defined in SecurityConfig
	 */
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	/**
	 * This bean is defined in SecurityConfig. Qualifer is
	 */
	@Autowired
	// @Qualifier("tokenServices")
	private AuthorizationServerTokenServices tokenServices;
/**
 * Defined in SecurityConfig
 * 
 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	/**
	 * This bean is defined in SecurityConfig
	 */
	@Autowired
	private TokenStore tokenStore;

	/**
	 * This bean is already defined in PersistenceConfig.java
	 */
	@Autowired
	private DataSource dataSource;
	/**
	 * Configured in SecurityConfig
	 */
	@Autowired
	private ClientDetailsService clientDetailsService;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security)
			throws Exception {
		security.passwordEncoder(passwordEncoder).tokenKeyAccess("permitAll()").checkTokenAccess(
				"isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients)
			throws Exception {
	//	clients.withClientDetails(clientDetailsService);
		clients.jdbc(dataSource);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		endpoints.authenticationManager(authenticationManager)
				// .tokenStore(tokenStore);
				.userDetailsService(userDetailsService)
				.tokenServices(tokenServices)
				.setClientDetailsService(clientDetailsService);
				;
	}

}
