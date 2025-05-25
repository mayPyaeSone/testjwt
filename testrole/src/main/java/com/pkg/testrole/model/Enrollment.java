package com.pkg.testrole.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Enrollment {
	public Enrollment(Course course, Users student) {
		super();
		this.course = course;
		this.student = student;
		this.enrolledAt = LocalDateTime.now();
	}

	public Enrollment() {
		super();
	}

	// id, course_id, student_id, enrolled
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	// Many-to-One relationship: Many enrollments are linked to one student (user)
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Users student;

	// Times tamp when the student enrolled
	private LocalDateTime enrolledAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Users getStudent() {
		return student;
	}

	public void setStudent(Users student) {
		this.student = student;
	}

	public LocalDateTime getEnrolledAt() {
		return enrolledAt;
	}

	public void setEnrolledAt(LocalDateTime enrolledAt) {
		this.enrolledAt = enrolledAt;
	}

	@Override
	public String toString() {
		return "Enrollment [id=" + id + ", course=" + course + ", student=" + student + ", enrolledAt=" + enrolledAt
				+ "]";
	}

}
