package com.imsoftware.students.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imsoftware.students.domain.StudentDTO;
import com.imsoftware.students.service.IStudentService;

@RestController
public class StudentController {

	@Autowired
	private IStudentService studentService;

	@GetMapping("/students")
	Collection<StudentDTO> all() {
		return studentService.findAll();
	}
	
	@GetMapping("/students/statistics")
	Collection<StudentDTO> allStatisticsStudents() {
		return studentService.findAllAndShowIfHaveAPopularSubject();
	}
}
