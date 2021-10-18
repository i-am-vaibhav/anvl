/**
 * 
 */
package com.anvl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
}
