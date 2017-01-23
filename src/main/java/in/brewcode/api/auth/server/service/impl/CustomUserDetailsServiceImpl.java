package in.brewcode.api.auth.server.service.impl;

import in.brewcode.api.auth.server.service.CustomUserDetailsService;
import in.brewcode.api.persistence.dao.IAdminAuthorDao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="userDetailsService")
@Transactional
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	@Autowired
	IAdminAuthorDao adminAuthorDao;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// write logic to fetch author and convert to user with author's role's
		// privileges to SimpleGrantedAuthority set and then create new
		// springsecurity's user object
		// and return in the place of null

		// Author author = adminAuthorDao.findByAuthorUserName(username);
		// author.getRole().getRolePrivileges();
		
		
		List<GrantedAuthority> listRoles = new ArrayList<GrantedAuthority>();
		
		SimpleGrantedAuthority role = new SimpleGrantedAuthority("ADMIN");
		//SimpleGrantedAuthority rol2 = new SimpleGrantedAuthority("USER");
		listRoles.add(role);
		//listRoles.add(rol2);
		UserDetails user = new User("john", "password",true, true, true, true,listRoles );
		return user;

	}

}
