package com.pkg.testrole.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pkg.testrole.dto.request.LoginRequestDTO;
import com.pkg.testrole.dto.request.UsersCreateDTO;
import com.pkg.testrole.dto.response.LoginResponseDTO;
import com.pkg.testrole.dto.response.UsersResponseDTO;
import com.pkg.testrole.model.Role;
import com.pkg.testrole.model.Users;
import com.pkg.testrole.repository.UserRepo;
import com.pkg.testrole.security.JWTService;
import com.pkg.testrole.security.UserPrincipal;

@Service
public class UserService {
	@Autowired
	private JWTService jwtService;

	@Autowired
	private UserRepo repo;

	@Autowired
	AuthenticationManager authManager;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public LoginResponseDTO verify(LoginRequestDTO userCreatDto) {

		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(userCreatDto.getUsername(), userCreatDto.getPassword()));
		if (authentication.isAuthenticated()) {
			Users user = new Users();
			user.setUsername(userCreatDto.getUsername());
			user.setPassword(userCreatDto.getPassword());
			user.setRole(userCreatDto.getRole());
			UserPrincipal userPrincipal = new UserPrincipal(user);
			String token = jwtService.generateToken(userPrincipal);
			//String token = "";
			return new LoginResponseDTO(token, userPrincipal.getRole());
		} else {
			throw new BadCredentialsException("Invalid username or password");
		}
	}

	public UsersResponseDTO register(UsersCreateDTO usersCreatDto) {
		Users user = new Users();
		user.setUsername(usersCreatDto.getUsername());
		user.setPassword(encoder.encode(usersCreatDto.getPassword()));
		
		user.setRole(Role.valueOf(usersCreatDto.getRole().toUpperCase()));
		
		Users saved = repo.save(user);
		UsersResponseDTO response = new UsersResponseDTO();
		response.setId(saved.getId());
		response.setUsername(saved.getUsername());
		response.setRole(saved.getRole().name());
		return response;

	}
}
