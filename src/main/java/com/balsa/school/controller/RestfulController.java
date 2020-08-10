package com.balsa.school.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.balsa.school.dao.AcademicRepository;
import com.balsa.school.dao.StudentRepository;
import com.balsa.school.dao.specs.StudentSpecifications;
import com.balsa.school.entity.Category;
import com.balsa.school.entity.Student;
import com.balsa.school.service.AcademicService;
import com.balsa.school.service.StudentService;

@RestController
@RequestMapping(path = "/api")
public class RestfulController {

	private StudentService studentService;
	private AcademicService academicService;
	private AcademicRepository academicRepository;
	private StudentRepository studentRepository;

	private Category category;
	private List<Integer> gradeId = new ArrayList<Integer>();

	public RestfulController(StudentService studentService, AcademicService academicService,
			AcademicRepository academicRepository, StudentRepository studentRepository) {
		this.studentService = studentService;
		this.academicService = academicService;
		this.academicRepository = academicRepository;
		this.studentRepository = studentRepository;

		gradeId.add(2);
		gradeId.add(3);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/students")
	@ResponseBody
	public List<Student> showStudents() {

		List<Student> students = studentRepository.findAll(StudentSpecifications.getByFullName("BALA"));
		
		return students;
	}
}
