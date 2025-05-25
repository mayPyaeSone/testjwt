package com.pkg.testrole.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.pkg.testrole.dto.request.CourseCreateDTO;
import com.pkg.testrole.dto.response.CourseResponseDTO;
import com.pkg.testrole.model.Course;
import com.pkg.testrole.model.Users;
import com.pkg.testrole.repository.CourseRepo;
import com.pkg.testrole.repository.UserRepo;

import jakarta.validation.Valid;

@Service
public class CourseService {
	@Autowired
	private CourseRepo courseRepository;

	@Autowired
	private UserRepo usersRepository;

	public CourseResponseDTO createCourse(CourseCreateDTO courseDTO) {
		Course course = new Course();
		course.setTitle(courseDTO.getTitle());
		course.setDescription(courseDTO.getDescription());

		Users instructor = usersRepository.findById(courseDTO.getInstructorId())
				.orElseThrow(() -> new RuntimeException("Instructor not found"));
		course.setInstructor(instructor);
		Course saved = courseRepository.save(course);

		CourseResponseDTO response = new CourseResponseDTO();
		response.setTitle(courseDTO.getTitle());
		response.setDescription(courseDTO.getDescription());
		response.setInstructorId(saved.getInstructor().getId());

		return response;
	}

	public List<CourseResponseDTO> getAllCourse() {
		List<Course> courseList = courseRepository.findAll();
		List<CourseResponseDTO> courseDtoList = new ArrayList<CourseResponseDTO>();
		for (Course c : courseList) {
			CourseResponseDTO courseDto = new CourseResponseDTO();
			courseDto.setTitle(c.getTitle());
			courseDto.setDescription(c.getDescription());
			courseDto.setInstructorId(c.getInstructor().getId());
			courseDtoList.add(courseDto);
		}
		return courseDtoList;

	}

	public CourseResponseDTO updateCourse(int courseId, @Valid CourseCreateDTO courseDTO, int currentUserId) {
		Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
		if (course.getInstructor().getId() != currentUserId) {
			throw new AccessDeniedException("You are not the owner of this course");
		}
		course.setTitle(courseDTO.getTitle());
		course.setDescription(courseDTO.getDescription());

		Course updated = courseRepository.save(course);

		CourseResponseDTO response = new CourseResponseDTO();
		response.setId(updated.getId());
		response.setTitle(updated.getTitle());
		response.setDescription(updated.getDescription());
		response.setInstructorId(updated.getInstructor().getId());
		return response;

	}

	public void deleteCourse(int courseId, int currentUserId) {
	    Course course = courseRepository.findById(courseId)
	        .orElseThrow(() -> new RuntimeException("Course not found"));

	    if (course.getInstructor().getId() != currentUserId) {
	        throw new AccessDeniedException("You are not the owner of this course");
	    }

	    courseRepository.delete(course);
	}
}
