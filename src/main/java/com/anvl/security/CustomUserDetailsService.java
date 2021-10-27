/**
 * 
 */
package com.anvl.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.anvl.entities.User;
import com.anvl.repos.UserRepository;

/**
 * @author Vaibhav
 *
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public AuthenticatedUser loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userByUserName = repository.findByUserName(username);
		if (userByUserName.isPresent()) {
			return new AuthenticatedUser(userByUserName.get());
		}
		throw new UsernameNotFoundException("user.not.found.ex.msg");
	}

}
