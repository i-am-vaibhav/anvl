/**
 * 
 */
package com.anvl.service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.anvl.entities.User;
import com.anvl.repos.UserRepository;

/**
 * @author Vaibhav
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	public ResponseEntity<List<User>> getUsers() {
		return ResponseEntity.ok(userRepo.findAll());
	}

	public ResponseEntity<List<User>> getUserByName(String name) {
		List<User> findByUserName = userRepo.findByUserName(name);
		if (findByUserName.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(findByUserName);
	}

	public ResponseEntity<String> createUser(User user) {
		URI location;
		try {
			User createdUser = userRepo.save(user);
			location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(createdUser.getId()).toUri();
		} catch (Exception e) {
			return ResponseEntity.internalServerError()
					.body("Facing issue while creating user, Please try again later");
		}
		return ResponseEntity.created(location).build();
	}

	public ResponseEntity<User> getUserById(BigDecimal id) {
		Optional<User> findByUserId = userRepo.findById(id);
		if (findByUserId.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(findByUserId.get());
	}

	public ResponseEntity<String> updateUser(BigDecimal id, User user) {
		URI location = null;
		try {
			Optional<User> optional = userRepo.findById(id);
			User updatedUser = null;
			if (optional.isEmpty()) {
				return ResponseEntity.notFound().build();
			} else {
				updatedUser = optional.get();
				updatedUser.setAddress(user.getAddress());
				updatedUser.setEmail(user.getEmail());
				updatedUser.setUserName(user.getUserName());
				userRepo.save(updatedUser);
			}
			location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(updatedUser.getId()).toUri();
		} catch (Exception e) {
			return ResponseEntity.internalServerError()
					.body("Facing issue while updating user, Please try again later");
		}
		return ResponseEntity.ok().location(location).build();
	}

}
