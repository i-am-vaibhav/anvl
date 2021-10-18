/**
 * 
 */
package com.anvl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
