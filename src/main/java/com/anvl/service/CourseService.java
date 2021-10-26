/**
 * 
 */
package com.anvl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anvl.repos.CourseRepository;

/**
 * @author Vaibhav
 *
 */
@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	public ResponseEntity<?> getCources() {
		return ResponseEntity.ok(courseRepository.findAll());
	}

}
