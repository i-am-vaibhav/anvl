package com.anvl.repos;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anvl.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, BigDecimal> {

	Optional<Course> findByName(String courseName);

}
