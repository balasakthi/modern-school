package com.balsa.school.service;

import java.util.List;

import com.balsa.school.entity.Student;

public interface StudentService {
	
	public List<Student> findAll();
	
	public Student findById(int theStudentId);
	
	public void save(Student theStudent);
	
	public void deleteById(int theStudentId);
	
	//public List<Student> findAll(StudentSpecification studentSpec);

}
