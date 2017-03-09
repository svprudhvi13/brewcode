package in.brewcode.api.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@EnableWebSecurity
@Configuration
@ComponentScan(basePackages = { "in.brewcode.api.auth" })
@EnableJpaRepositories(basePackages = "in.brewcode.api.auth.server.dao", queryLookupStrategy = Key.CREATE_IF_NOT_FOUND)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * wires to CustomUserDetailsService
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Defined persistence config
	 */
	@Autowired
	private DataSource dataSource;

	/**
	 * Written seperately to use in ClientDetailsService and
	 * ClientRegistrationService bean as it extends both these interfaces
	 * */

	private JdbcClientDetailsService getJdbcClientDetailsService() {
		JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(
				dataSource);
		jdbcClientDetailsService.setPasswordEncoder(getPasswordEncoder());
		return jdbcClientDetailsService;
	}

	@Bean
	public ClientDetailsService clientDetailsService() {
		 ClientDetailsService cds = getJdbcClientDetailsService();
return cds;
	}

	/**
	 * Used for client registration, but defined here
	 */
	@Bean
	public ClientRegistrationService clientRegistrationService() {
		ClientRegistrationService crs = getJdbcClientDetailsService();
		return crs;
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		AuthenticationManager authenticationManager = super
				.authenticationManager();
		return authenticationManager;
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Primary
	public DefaultTokenServices getTokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(getTokenStore());
		tokenServices.setSupportRefreshToken(true);
		return tokenServices;
	}

	@Bean
	public TokenStore getTokenStore() {
		final JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
		return jdbcTokenStore;
	}

	@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth)
			throws Exception {
		// @formatter:off
		auth.userDetailsService(userDetailsService).passwordEncoder(
				getPasswordEncoder());
		;
		// .jdbcAuthentication().dataSource(dataSource);
		// .inMemoryAuthentication().withUser("john").password("123").roles("USER").and().withUser("tom")
		// .password("111").roles("ADMIN");
		// @formatter:on
	}
	/*
	 * @Override public void configure(HttpSecurity http) throws Exception{
	 * http.authorizeRequests()//.antMatchers("/login").permitAll()
	 * .antMatchers("/admin/**").permitAll()//hasRole("ADMIN")//.authenticated()
	 * .antMatchers("/author/**").hasRole("USER")
	 * //.and().formLogin().permitAll() ; }
	 */

}
