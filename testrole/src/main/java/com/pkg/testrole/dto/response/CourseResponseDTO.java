package com.pkg.testrole.dto.response;

public class CourseResponseDTO {
	private int id;
	private String title;
	private String description;
	private int instructorId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}

	@Override
	public String toString() {
		return "CourseResponseDTO [id=" + id + ", title=" + title + ", description=" + description + ", instructorId="
				+ instructorId + "]";
	}



}
