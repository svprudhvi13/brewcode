package in.brewcode.api.auth.resource.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	@Autowired
	private ResourceServerTokenServices tokenServices;
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
	resources.tokenServices(tokenServices);

	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()//.antMatchers("/login").permitAll()
		.antMatchers("/admin/**")//.access("hasRole('ADMIN')")
		.permitAll()
		//.hasRole("ADMIN")
		.antMatchers("/author/**").access("hasRole('AUTHOR') or hasRole('USER')")
		.anyRequest().permitAll()
		
		//.permitAll()
		//.hasRole("USER")
//		.authenticated()
//		.and().userDetailsService(userDetailsService)
	//	.httpBasic().disable();
		//.and().formLogin().permitAll()
		;

	}
}
