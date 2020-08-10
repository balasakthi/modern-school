package com.balsa.school.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.balsa.school.dao.StudentRepository;
import com.balsa.school.entity.Student;
import com.balsa.school.service.StudentService;

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

	

	/*@Override
	public List<Student> findByCriteria(String name) {
		return studentRepository.findAll(new Specification<Student>() {
			@Override
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (name != null) {
					  predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("fullName"), "%" + name + "%")));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
	}*/
	
	

}
