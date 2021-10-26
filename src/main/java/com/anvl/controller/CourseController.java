/**
 * 
 */
package com.anvl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anvl.security.Roles;
import com.anvl.service.CourseService;

/**
 * @author DELL
 *
 */
@RestController
@RequestMapping("/v1/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@GetMapping()
	@Secured({ Roles.ADMIN, Roles.USER })
	public ResponseEntity<?> getCourses() {
		return courseService.getCources();
	}

}
