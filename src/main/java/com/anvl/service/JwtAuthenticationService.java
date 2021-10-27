/**
 * 
 */
package com.anvl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anvl.exception.AuthenticationException;
import com.anvl.security.AuthenticatedUser;
import com.anvl.security.CustomUserDetailsService;
import com.anvl.security.JwtRequest;
import com.anvl.security.JwtResponse;
import com.anvl.security.JwtUtil;

/**
 * @author DELL
 *
 */
@Service
public class JwtAuthenticationService {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil util;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder encoder;

	public ResponseEntity<JwtResponse> authenticate(JwtRequest request) throws AuthenticationException {
		JwtResponse response = null;
		try {
			String username = request.getUsername();
			AuthenticatedUser userDetails = userDetailsService.loadUserByUsername(username);
			if (userDetails != null) {
				String password = request.getPassword();
				if (encoder.matches(password, userDetails.getPassword())) {

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, request.getPassword(), userDetails.getAuthorities());
					authenticationManager.authenticate(authentication);
				} else {
					throw new AuthenticationException("invalid.credential.except.msg");
				}
			} else {
				throw new AuthenticationException("invalid.credential.except.msg");
			}
			response = new JwtResponse(util.generateToken(userDetails.getUsername()));
		}catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException("user.not.found.ex.msg");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationException("invalid.credential.except.msg");
		}
		return ResponseEntity.ok(response);
	}

}
