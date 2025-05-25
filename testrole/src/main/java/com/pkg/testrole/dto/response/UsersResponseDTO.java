package com.pkg.testrole.dto.response;

public class UsersResponseDTO {
	private int id;
	private String username;
	private String role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "UsersResponseDTO [id=" + id + ", username=" + username + ", role=" + role + "]";
	}
	
}
