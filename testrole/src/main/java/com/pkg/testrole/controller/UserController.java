package com.pkg.testrole.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pkg.testrole.dto.request.LoginRequestDTO;
import com.pkg.testrole.dto.request.UsersCreateDTO;
import com.pkg.testrole.dto.response.LoginResponseDTO;
import com.pkg.testrole.dto.response.UsersResponseDTO;
import com.pkg.testrole.service.UserService;

import jakarta.validation.Valid;

@RestController

public class UserController {
	@Autowired
	private UserService usersService;

	@GetMapping("/")
	public String hello() {
		return "Hello";
	}

	@PostMapping("auth/register")
	public ResponseEntity<UsersResponseDTO> register(@RequestBody  UsersCreateDTO user) {

		try {
			UsersResponseDTO savedUser = usersService.register(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

	}

	@PostMapping("auth/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO user) {
		LoginResponseDTO response = usersService.verify(user);
		return ResponseEntity.ok(response);
	}

}