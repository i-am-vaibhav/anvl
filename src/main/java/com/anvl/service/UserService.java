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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.anvl.entities.Course;
import com.anvl.entities.User;
import com.anvl.repos.CourseRepository;
import com.anvl.repos.UserRepository;

/**
 * @author Vaibhav
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private MessageService messageService;

	public ResponseEntity<List<User>> getUsers() {
		return ResponseEntity.ok(userRepo.findAll());
	}

	public ResponseEntity<Object> getUserByName(String name) {
		Optional<User> findByUserName = userRepo.findByUserName(name);
		if (findByUserName.isEmpty()) {
			return ResponseEntity.status(404).body(getMessage("user.not.found.exception.msg"));
		}
		return ResponseEntity.ok(findByUserName.get());
	}

	public ResponseEntity<String> createUser(User user) {
		URI location;
		try {
			user.setPassword("$2a$10$p3PHnpoBAnzZiL8mr3gMieMhVVSb585ajC2ZsBB0kwb4KvZKFSdNi");
			User createdUser = userRepo.save(user);
			location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(createdUser.getId()).toUri();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(getMessage("user.create.exception.msg"));
		}
		return ResponseEntity.created(location).build();
	}

	private String getMessage(String property) {
		return messageService.getMsg(property);
	}

	public ResponseEntity<Object> getUserById(BigDecimal id) {
		Optional<User> findByUserId = userRepo.findById(id);
		if (findByUserId.isEmpty()) {
			return ResponseEntity.status(404).body(getMessage("user.not.found.exception.msg"));
		}
		return ResponseEntity.ok(findByUserId.get());
	}

	public ResponseEntity<String> updateUser(BigDecimal id, User user) {
		URI location = null;
		try {
			Optional<User> userById = userRepo.findById(id);
			User updatedUser = null;
			if (userById.isEmpty()) {
				return ResponseEntity.status(404).body(getMessage("user.not.found.exception.msg"));
			} else {
				updatedUser = userById.get();
				updatedUser.setAddress(user.getAddress());
				updatedUser.setEmail(user.getEmail());
				updatedUser.setUserName(user.getUserName());
				userRepo.save(updatedUser);
			}
			location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(updatedUser.getId()).toUri();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(getMessage("user.update.exception.msg"));
		}
		return ResponseEntity.ok().location(location).build();
	}

	public ResponseEntity<Object> getCoursesByUserId(BigDecimal id) {
		Optional<User> userById = userRepo.findById(id);
		if (userById.isEmpty()) {
			return ResponseEntity.status(404).body(getMessage("user.not.found.exception.msg"));
		}
		User user = userById.get();
		return ResponseEntity.ok(user.getCources());
	}

	public ResponseEntity<String> enroll(String courseName, BigDecimal id) {
		Optional<User> userById = userRepo.findById(id);
		if (userById.isEmpty()) {
			return ResponseEntity.status(404).body(getMessage("user.not.found.exception.msg"));
		}
		User user = userById.get();
		Optional<Course> courseByName = courseRepo.findByName(courseName);
		if (courseByName.isEmpty()) {
			return ResponseEntity.status(404).body(getMessage("course.not.found.exception.msg"));
		}
		List<Course> cources = user.getCources();
		cources.add(courseByName.get());
		user.setCources(cources);
		userRepo.save(user);
		return ResponseEntity.ok(messageService.getMsg("course.enroll.msg", user.getUserName(), courseName));
	}

	public ResponseEntity<String> enroll(String courseName) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> optional = userRepo.findByUserName(authentication.getName());
		return enroll(courseName, optional.get().getId());
	}

}
