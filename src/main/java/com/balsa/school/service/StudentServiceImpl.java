package com.balsa.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.balsa.school.dao.StudentRepository;
import com.balsa.school.entity.Student;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	@Override
	public Student findById(int theStudentId) {
		Optional<Student> result = studentRepository.findById(theStudentId);
		Student theStudent = null;
		if (result.isPresent()) {
			theStudent = result.get();
		} else {
			throw new RuntimeException("Student Not Found- " + theStudentId);
		}
		return theStudent;
	}

	@Override
	public void save(Student theStudent) {
		studentRepository.save(theStudent);

	}

	@Override
	public void deleteById(int theStudentId) {
		studentRepository.deleteById(theStudentId);

	}

}
