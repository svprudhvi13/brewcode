package in.brewcode.api.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@EnableWebSecurity
@Configuration
@ComponentScan(basePackages={"in.brewcode.api.auth"})
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	/**
	 * Defined persistence config
	 */
	@Autowired
	private DataSource dataSource;
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception{
		AuthenticationManager authenticationManager = super.authenticationManager();
		return authenticationManager;
	}
	
	@Bean
	public ClientDetailsService getClientDetailsService(){
	
		ClientDetailsService clientDetailsService = null;
		
			clientDetailsService = new JdbcClientDetailsService(dataSource);
		return clientDetailsService;
		 
	}
	
	@Bean
	@Primary
	public DefaultTokenServices getTokenServices(){
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(getTokenStore());
		tokenServices.setSupportRefreshToken(true);
		return tokenServices;
	}
	@Bean
	public TokenStore getTokenStore(){
		final JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
		return jdbcTokenStore;
	}
	@Autowired
    public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
		auth.userDetailsService(userDetailsService);
		//.jdbcAuthentication().dataSource(dataSource);
		//.inMemoryAuthentication().withUser("john").password("123").roles("USER").and().withUser("tom")
			//.password("111").roles("ADMIN");
		// @formatter:on
    }
	/*@Override
	public void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()//.antMatchers("/login").permitAll()
			.antMatchers("/admin/**").permitAll()//hasRole("ADMIN")//.authenticated()
			.antMatchers("/author/**").hasRole("USER")
			//.and().formLogin().permitAll()
			;
	}*/
	/**
	 * Used for client registration, but defined here
	 */
	@Bean
	public ClientRegistrationService clientRegistrationService(){
		return new JdbcClientDetailsService(dataSource);
	}
}
