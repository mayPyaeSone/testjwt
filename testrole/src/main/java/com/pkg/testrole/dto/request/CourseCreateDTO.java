package com.pkg.testrole.dto.request;

public class CourseCreateDTO {
	private String title;
	private String description;
	private int instructorId;

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
		return "CourseCreateDTO [title=" + title + ", description=" + description + ", instructorId=" + instructorId
				+ "]";
	}

}
