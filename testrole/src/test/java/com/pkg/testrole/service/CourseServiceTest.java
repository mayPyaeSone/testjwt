package com.pkg.testrole.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.pkg.testrole.dto.request.CourseCreateDTO;
import com.pkg.testrole.dto.response.CourseResponseDTO;
import com.pkg.testrole.model.Course;
import com.pkg.testrole.model.Users;
import com.pkg.testrole.repository.CourseRepo;
import com.pkg.testrole.repository.UserRepo;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @Mock
    private CourseRepo courseRepository;

    @Mock
    private UserRepo userRepository;

    @InjectMocks
    private CourseService courseService;
    
    @Test
    public void testCreateCourse_success() {
        CourseCreateDTO dto = new CourseCreateDTO();
        dto.setTitle("Test Course");
        dto.setDescription("Test Description");
        dto.setInstructorId(1);

        Users instructor = new Users();
        instructor.setId(1);

        Course savedCourse = new Course();
        savedCourse.setId(1);
        savedCourse.setTitle(dto.getTitle());
        savedCourse.setDescription(dto.getDescription());
        savedCourse.setInstructor(instructor);

        when(userRepository.findById(1)).thenReturn(Optional.of(instructor));
        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        CourseResponseDTO response = courseService.createCourse(dto);

        assertEquals("Test Course", response.getTitle());
        assertEquals(1, response.getInstructorId());
    }
}
