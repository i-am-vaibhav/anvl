package com.anvl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anvl.exception.AuthenticationException;
import com.anvl.service.JwtAuthenticationService;

@CrossOrigin
@RestController
public class JwtAuthenticationController {

	@Autowired
	private JwtAuthenticationService service;

	@PostMapping("/authenticate")
	public ResponseEntity<JwtResponse> authenticate(@Validated @RequestBody JwtRequest request)
			throws AuthenticationException {
		return service.authenticate(request);
	}

}
