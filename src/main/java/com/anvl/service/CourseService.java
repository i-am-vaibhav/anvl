/**
 * 
 */
package com.anvl.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.anvl.entities.Course;
import com.anvl.repos.CourseRepository;

/**
 * @author Vaibhav
 *
 */
@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private MessageService msgService;

	public ResponseEntity<?> getCources() {
		return ResponseEntity.ok(courseRepository.findAll());
	}

	public ResponseEntity<?> createCourse(Course course) {
		URI location;
		try {
		Course savedCourse = courseRepository.save(course);
		location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCourse.getId()).toUri();
	} catch (Exception e) {
		return ResponseEntity.internalServerError().body(msgService.getMsg("course.create.exception.msg"));
	}
	return ResponseEntity.created(location).build();
	}

}
