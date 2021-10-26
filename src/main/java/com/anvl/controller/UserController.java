/**
 * 
 */
package com.anvl.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anvl.entities.User;
import com.anvl.security.Roles;
import com.anvl.service.UserService;

/**
 * @author Vaibhav
 *
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	@Secured(Roles.ADMIN)
	public ResponseEntity<List<User>> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/{id}")
	@Secured(Roles.ADMIN)
	public ResponseEntity<Object> getUserById(@PathVariable BigDecimal id) {
		return userService.getUserById(id);
	}

	@GetMapping("/my-profile")
	@Secured({ Roles.ADMIN, Roles.USER })
	public ResponseEntity<Object> getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userService.getUserByName(authentication.getName());
	}
	
	@GetMapping("/{id}/courses")
	@Secured({ Roles.ADMIN, Roles.USER })
	public ResponseEntity<Object> getCoursesByUserId(@PathVariable BigDecimal id) {
		return userService.getCoursesByUserId(id);
	}

	@PostMapping
	@Secured(Roles.ADMIN)
	public ResponseEntity<String> createUser(@Validated @RequestBody User user) {
		return userService.createUser(user);
	}

	@PutMapping("/{id}")
	@Secured(Roles.ADMIN)
	public ResponseEntity<String> updateUser(@Validated @RequestBody User user, @PathVariable BigDecimal id) {
		return userService.updateUser(id, user);
	}

}
