//package com.pkg.testrole.controller;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.http.MediaType;
//
//
//import com.pkg.testrole.dto.response.CourseResponseDTO;
//import com.pkg.testrole.service.CourseService;
//@WebMvcTest(CourseController.class)
//public class CourseControllerTest {
//	@Autowired
//    private MockMvc mockMvc;
//
//    @SuppressWarnings("removal")
//	@MockBean
//    private CourseService courseService;
//    
//    @Test
//    public void testGetAllCourses() throws Exception {
//        CourseResponseDTO course = new CourseResponseDTO();
//        course.setTitle("Java 101");
//        course.setDescription("Intro course");
//        course.setInstructorId(1);
//
//        when(courseService.getAllCourse()).thenReturn(List.of(course));
//
//        mockMvc.perform(get("/courses")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].title").value("Java 101"));
//    }
//}
