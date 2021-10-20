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

	public ResponseEntity<User> createUser(User user) {
		User createdUser = userRepo.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	public ResponseEntity<User> getUserById(BigDecimal id) {
		Optional<User> findByUserId = userRepo.findById(id);
		if (findByUserId.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(findByUserId.get());
	}
}
