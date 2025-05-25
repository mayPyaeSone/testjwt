package com.pkg.testrole.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UsersCreateDTO {
	@NotBlank(message = "Username is required")
	private String username;
	@NotBlank(message = "Password is required")
	private String password;
	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UsersCreateDTO [username=" + username + ", password=" + password + ", role=" + role + "]";
	}

}
