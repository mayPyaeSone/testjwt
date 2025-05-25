package com.pkg.testrole.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pkg.testrole.model.Course;
import com.pkg.testrole.model.Enrollment;
import com.pkg.testrole.model.Users;

@Repository
public interface EnrollmentRepository  extends JpaRepository<Enrollment, Long> {
	 List<Enrollment> findByCourse_Id(Long courseId);
	 boolean existsByCourseAndStudent(Course course, Users student);
	 List<Enrollment> findByStudent_Id(Integer studentId);
	 

}


