package com.pkg.testrole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pkg.testrole.model.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {

}
