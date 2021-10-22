/**
 * 
 */
package com.anvl.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anvl.entities.User;
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
	public ResponseEntity<List<User>> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable BigDecimal id) {
		return userService.getUserById(id);
	}

	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@RequestBody User user,@PathVariable BigDecimal id) {
		return userService.updateUser(id,user);
	}

}
