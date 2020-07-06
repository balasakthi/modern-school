package com.balsa.school.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.balsa.school.entity.Student;
import com.balsa.school.service.StudentService;

@RestController
@RequestMapping(path = "/api")
public class RestfulController {

	private StudentService studentService;

	public RestfulController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/students")
	public List<Student> showAll(){
		return studentService.findAll();
	}
}
