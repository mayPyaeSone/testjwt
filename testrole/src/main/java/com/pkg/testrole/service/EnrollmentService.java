package com.pkg.testrole.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pkg.testrole.dto.response.CourseResponseDTO;
import com.pkg.testrole.model.Course;
import com.pkg.testrole.model.Enrollment;
import com.pkg.testrole.model.Users;
import com.pkg.testrole.repository.CourseRepo;
import com.pkg.testrole.repository.EnrollmentRepository;
import com.pkg.testrole.repository.UserRepo;

@Service
public class EnrollmentService {
	@Autowired
	private CourseRepo courseRepository;

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	public void enrollStudent(Integer courseId, int studentId) {
		Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

		Users student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

		// Check if already enrolled
		boolean exists = enrollmentRepository.existsByCourseAndStudent(course, student);
		if (exists) {
			throw new IllegalStateException("You are already enrolled in this course.");
		}

		Enrollment enrollment = new Enrollment(course, student);
		enrollmentRepository.save(enrollment);
	}

	public List<CourseResponseDTO> getCoursesByStudentId(Integer studentId) {
		List<Enrollment> enrollments = enrollmentRepository.findByStudent_Id(studentId);

		return enrollments.stream().map(e -> {
			Course c = e.getCourse();
			CourseResponseDTO dto = new CourseResponseDTO();
			dto.setId(c.getId());
			dto.setTitle(c.getTitle());
			dto.setDescription(c.getDescription());
			dto.setInstructorId(c.getInstructor().getId());
			return dto;
		}).collect(Collectors.toList());
	}

}
