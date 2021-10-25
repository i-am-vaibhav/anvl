package com.anvl.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.anvl.entities.Role;
import com.anvl.entities.User;

public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	private User user;

	public AuthenticatedUser(User user) {
		super(user.getUserName(), user.getPassword(), getAuthorities(user));
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		Set<String> roleAndPermissions = new HashSet<>();
		for (Role role : user.getRoles()) {
			roleAndPermissions.add("ROLE_" + role.getName());
		}
		String[] roleNames = new String[roleAndPermissions.size()];
		return AuthorityUtils.createAuthorityList(roleAndPermissions.toArray(roleNames));
	}
}