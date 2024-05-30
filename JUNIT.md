<h1>1. SIMPLE JUNIT </h1>

package com.JunitTesting.Service;

import com.JunitTesting.Dto.StudentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StudentServiceTest {

    @MockBean
    public StudentService studentService;

    @BeforeEach
    public void setup(){
        List<StudentDTO> studentList = Arrays.asList(new StudentDTO("luke",69,"mech"),
                new StudentDTO("sid",04,"comps"),
                new StudentDTO("praful",02,"elec"));
        when(studentService.getStudent(2)).thenReturn(studentList.get(0));
        when(studentService.getStudentList()).thenReturn(studentList);
    }

    @Test
    public void getStudentTest() {
        StudentDTO actual = new StudentDTO("luke",2,"mech");
        StudentDTO student = studentService.getStudent(2);
        assertEquals(actual,student);
    }

    @Test
    public void listStudents(){
        List<StudentDTO> actual = Arrays.asList(new StudentDTO("luke",69,"mech"),
                new StudentDTO("sid",04,"comps"),
                new StudentDTO("praful",02,"elec"));
        List<StudentDTO> students = studentService.getStudentList();
        assertEquals(actual,students);
    }

    @Test
    public void addStudentTest(){
        StudentDTO newStudent = new StudentDTO("emmy",69,"IT");

        when(studentService.addStudent(newStudent)).thenReturn(newStudent.toString());
        assertEquals(newStudent.toString(), studentService.addStudent(newStudent));
    }

}

<h1>2. TESTING RESTTEMPLATE</h1>

package com.recruiter.recruiter.RecruiterServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.recruiter.recruiter.DTO.*;
import com.recruiter.recruiter.Models.RecruiterModel;
import com.recruiter.recruiter.Repo.RecRepo;
import com.recruiter.recruiter.Services.RecruiterService;

@SpringBootTest
public class RecruiterServiceTest {

    @MockBean
    private RecRepo recRepo;

    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private ModelMapper mapper;

    @MockBean
    private RecruiterService recruiterService;



    public List<RecruiterModel> allRecruiters() {
        // Creating UserDTO objects
        UserDTO johnDoeUser = new UserDTO(1L, "John Doe", "john@example.com", "password123");
        UserDTO janeSmithUser = new UserDTO(2L, "Jane Smith", "jane@example.com", "pass456");
        UserDTO bobJohnsonUser = new UserDTO(3L, "Bob Johnson", "bob@example.com", "bobPass");

        // Creating CompanyDTO objects
        CompanyDTO googleCompany = new CompanyDTO(1L, "Google","Google");
        CompanyDTO microsoftCompany = new CompanyDTO(2L, "Microsoft", "Microsoft");
        CompanyDTO amazonCompany = new CompanyDTO(3L, "Amazon", "Amazon");

        // Creating RecruiterDTO objects with associated UserDTO and CompanyDTO
        // return Arrays.asList(
        //     new RecruiterModel(johnDoeUser, 1L, 1L, "Software Engineer", 1L, googleCompany),
        //     new RecruiterModel(janeSmithUser, 2L, 2L, "HR Manager", 2L, microsoftCompany),
        //     new RecruiterModel(bobJohnsonUser, 3L, 3L, "Recruitment Specialist", 3L, amazonCompany)
        // );

        return Arrays.asList(
            new RecruiterModel(1L, 1L, "Software Engineer", 1L),
            new RecruiterModel( 2L, 2L, "HR Manager", 2L),
            new RecruiterModel( 3L, 3L, "Recruitment Specialist", 3L)
        );
    }


    public ResponseEntity<UserDTO> user() {
        UserDTO userDTO = new UserDTO(1L, "John Doe", "john@example.com", "password123");
        return ResponseEntity.ok(userDTO);
    }

    public ResponseEntity<CompanyDTO> company() {
        CompanyDTO companyDTO = new CompanyDTO(1L, "Google","Google");
        return ResponseEntity.ok(companyDTO);
    }

    @Test
    public void test_success_getAllRecruiters() {
        // Mock responses
        when(recRepo.findAll()).thenReturn(allRecruiters());
        when(restTemplate.exchange(
            "http://localhost:8003/user/get/" + 1L,
            HttpMethod.GET,
            null,
            UserDTO.class
        )).thenReturn(ResponseEntity.ok(new UserDTO(1L, "John Doe", "john@example.com", "password123")));
        when(restTemplate.exchange(
            "http://localhost:8001/company/" + 1L,
            HttpMethod.GET,
            null,
            CompanyDTO.class
        )).thenReturn(ResponseEntity.ok(new CompanyDTO(1L, "Google", "Google")));

        // Call the method under test
        ResponseEntity<?> response = recruiterService.getAllRecruiters();

        // Verify the response
        //assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        // Verify that the mapper.map() method is called for each recruiter
        verify(mapper, times(3)).map(any(RecruiterModel.class), eq(RecruiterDTO.class));
    }

}
