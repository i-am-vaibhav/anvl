package com.anvl.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "ANVL token". Remove Bearer word and get only the
		// Token
		if (!request.getRequestURI().endsWith("/authenticate") && !request.getRequestURI().endsWith("/home")) {
			if (requestTokenHeader != null && requestTokenHeader.startsWith("ANVL ")) {
				jwtToken = requestTokenHeader.substring(5);
				try {
					username = jwtTokenUtil.getUserName(jwtToken);
				} catch (IllegalArgumentException e) {
					throw new JwtException("jwt.unable.ex.msg");
				} catch (ExpiredJwtException e) {
					throw new JwtException("jwt.token.expired.ex.msg");
				} catch (MalformedJwtException e) {
					throw new JwtException("jwt.token.invalid.ex.msg");
				}catch (Exception e) {
					throw new JwtException("jwt.token.invalid.ex.msg");
				}
			} else {
				throw new JwtException("jwt.not.begin.ex.msg");
			}
		}
		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(userDetails);
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
