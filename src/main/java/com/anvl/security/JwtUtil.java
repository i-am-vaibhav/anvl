/**
 * 
 */
package com.anvl.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Vaibhav
 *
 */
@Component
public final class JwtUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Value("${jwt.secret.key:anvl}")
	private String secretKey;

	@Value("${jwt.session.validity.mins:5}")
	private int tokenValidity;

	private JwtParser parser = Jwts.parser();

	public String getUserName(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDate(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUserName(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String generateToken(String subject) {
		return Jwts.builder().setClaims(new HashMap<>()).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + tokenValidity * 60 * 1000))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}

	private boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDate(token);
		return expiration.before(new Date());
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimTaker) {
		Claims claims = getAllClaimsFromToken(token);
		return claimTaker.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return parser.setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

}
