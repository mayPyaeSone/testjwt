package com.pkg.testrole.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pkg.testrole.dto.request.UsersCreateDTO;
import com.pkg.testrole.dto.response.UsersResponseDTO;
import com.pkg.testrole.repository.CourseRepo;
import com.pkg.testrole.repository.EnrollmentRepository;
import com.pkg.testrole.repository.UserRepo;
import com.pkg.testrole.security.JWTService;
import com.pkg.testrole.security.MyUserDetailsService;
import com.pkg.testrole.service.UserService;

@ImportAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService usersService;
	//private final ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
    private ObjectMapper objectMapper;
	@MockBean
	private JWTService jwtService;
	@MockBean
	private UserRepo userRepo;
	@MockBean
	private CourseRepo couseRepo;
	@MockBean
	private EnrollmentRepository enromentRepo;
	@MockBean
	private MyUserDetailsService myUserDetailsService;

	@Test
	public void testRegister_Success() throws Exception {
		//request body
		UsersCreateDTO request = new UsersCreateDTO();
		request.setUsername("john");
		request.setPassword("123456");
		request.setRole("STUDENT");
		//response body
		UsersResponseDTO response = new UsersResponseDTO();
		response.setId(1);
		response.setUsername("john");
		response.setRole("STUDENT");
		//log in user
		String username = "Min";
		UserDetails userDetails = User
				.withUsername("Min")
				.password("Min@123")
				.roles("ADMIN")
				.build(); // Mocked UserDetails
		when(jwtService.extractUserName(any(String.class))).thenReturn("Min");
		when(myUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
		when(jwtService.validateToken(Mockito.anyString(),any())).thenReturn(true);
		when(usersService.register(any(UsersCreateDTO.class))).thenReturn(response);
		mockMvc.perform(post("/auth/register")
				.header("Authorization","Bearer " )
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
		 .andExpect(jsonPath("$.id").value(1))
         .andExpect(jsonPath("$.username").value("john"))
         .andExpect(jsonPath("$.role").value("STUDENT"));
	}
	@Test
	public void testRegister_roleIsNotADMIN_failed() throws Exception {
		//request body
		UsersCreateDTO request = new UsersCreateDTO();
		request.setUsername("john");
		request.setPassword("123456");
		request.setRole("STUDENT");

		//log in userSTUDENT
		String username = "Min";
		UserDetails userDetails = User
				.withUsername("Min")
				.password("Min@123")
				.roles("STUDENT")
				.build(); // Mocked UserDetails
		when(jwtService.extractUserName(any(String.class))).thenReturn("Min");
		when(myUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
		mockMvc.perform(post("/auth/register")
				.header("Authorization","Bearer " )
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isUnauthorized()); 
	}
	@Test
	public void testRegister_requestBodyisWrong_failed() throws Exception {
		//request body
		UsersCreateDTO request = new UsersCreateDTO();
		
		//log in user
		String username = "Min";
		UserDetails userDetails = User
				.withUsername("Min")
				.password("Min@123")
				.roles("ADMIN")
				.build(); // Mocked UserDetails
		when(jwtService.extractUserName(any(String.class))).thenReturn("Min");
		when(myUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
		when(jwtService.validateToken(Mockito.anyString(),any())).thenReturn(true);
		when(usersService.register(Mockito.any(UsersCreateDTO.class)))
		.thenThrow(new RuntimeException("Service failed"));
		mockMvc.perform(post("/auth/register")
				.header("Authorization","Bearer " )
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest())
        .andExpect(content().string("")); 
	}
}
