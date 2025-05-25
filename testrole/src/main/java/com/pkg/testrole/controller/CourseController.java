package com.pkg.testrole.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import com.pkg.testrole.dto.request.CourseCreateDTO;
import com.pkg.testrole.dto.response.CourseResponseDTO;
import com.pkg.testrole.security.UserPrincipal;
//import com.pkg.testrole.security.UserPrincipal;
import com.pkg.testrole.service.CourseService;

import jakarta.validation.Valid;

@RestController
public class CourseController {

	@Autowired
	CourseService service;

	@PostMapping("courses")
	public ResponseEntity<?> createCourse(@RequestBody CourseCreateDTO courseDTO) {
		try {
			CourseResponseDTO savedCourse = service.createCourse(courseDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("courses")
	public ResponseEntity<List<CourseResponseDTO>> getCourses(){
		List<CourseResponseDTO> courseList = service.getAllCourse();
	    return ResponseEntity.ok(courseList);
	}
	
	@PutMapping("/courses/{id}")
	public ResponseEntity<?> updateCourse(
	        @PathVariable int id,
	        @RequestBody @Valid CourseCreateDTO courseDTO,
	        Authentication authentication) {
		UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

	    int currentUserId = userDetails.getId();

	    try {
	        CourseResponseDTO updated = service.updateCourse(id, courseDTO, currentUserId);
	        return ResponseEntity.ok(updated);
	    } catch (AccessDeniedException e) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    }
	}
	
	@DeleteMapping("/courses/{id}")
	public ResponseEntity<?> deleteCourse(
	        @PathVariable int id,
	        Authentication authentication) {

	    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
	    int currentUserId = userPrincipal.getId();

	    try {
	    	service.deleteCourse(id, currentUserId);
	        return ResponseEntity.noContent().build(); // 204 No Content
	    } catch (AccessDeniedException e) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    }
	}

}
