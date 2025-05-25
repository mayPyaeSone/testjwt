package com.pkg.testrole.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pkg.testrole.dto.response.CourseResponseDTO;
import com.pkg.testrole.security.UserPrincipal;
import com.pkg.testrole.service.EnrollmentService;

@RestController
public class EnrollmentController {

	@Autowired
	private EnrollmentService enrollmentService;

	@PostMapping("/courses/{courseId}/enroll")
	public ResponseEntity<?> enrollInCourse(@PathVariable Integer courseId, Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		int studentId = userPrincipal.getId();
		try {
			enrollmentService.enrollStudent(courseId, studentId);
			return ResponseEntity.status(HttpStatus.CREATED).body("Enrollment successful.");
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/students/{studentId}/courses")
	public ResponseEntity<?> getStudentCourses(@PathVariable Integer studentId, Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		 boolean isOwner = userPrincipal.getId() == studentId;
		    boolean isAdmin = userPrincipal.getRole().equalsIgnoreCase("ADMIN");

		    if (!isOwner && !isAdmin) {
		        return ResponseEntity.status(HttpStatus.FORBIDDEN)
		                             .body("You are not allowed to access this student's courses.");
		    }
	    List<CourseResponseDTO> courses = enrollmentService.getCoursesByStudentId(studentId);
	    return ResponseEntity.ok(courses);
	}


}
