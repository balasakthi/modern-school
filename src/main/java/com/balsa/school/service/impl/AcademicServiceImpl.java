package com.balsa.school.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.balsa.school.dao.AcademicRepository;
import com.balsa.school.dao.specs.AcademicSpecification;
import com.balsa.school.entity.Academic;
import com.balsa.school.entity.Category;
import com.balsa.school.entity.Grade;
import com.balsa.school.entity.Student;
import com.balsa.school.service.AcademicService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class AcademicServiceImpl implements AcademicService {

	private AcademicRepository academicRepository;

	public AcademicServiceImpl(AcademicRepository academicRepository) {
		this.academicRepository = academicRepository;
	}

	@Override
	public Academic findById(int theAcademicId) {
		Optional<Academic> result = academicRepository.findById(theAcademicId);
		Academic theAcademic = null;
		if (result.isPresent()) {
			theAcademic = result.get();
		} else {
			throw new RuntimeException("Academic Not Found-" + theAcademicId);
		}
		return theAcademic;
	}


	@Override
	public void save(Academic theAcademic) {

		academicRepository.save(theAcademic);
	}

	

	@Override
	public List<Academic> findByCriteria(String fullName, List<Integer> gradeId) {
		return academicRepository.findAll(new Specification<Academic>() {
			@Override
			public Predicate toPredicate(Root<Academic> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				if (gradeId != null && !gradeId.isEmpty()) {
					predicates.add(root.get("grade").in(gradeId));
				}
				if (fullName != null) {
					predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("student"), "%" + 1 + "%")));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
	}

	@Override
	public List<Academic> findAll(AcademicSpecification academicSpecification) {
		return academicRepository.findAll(academicSpecification);
	}

	@Override
	public List<Academic> findAll() {
		
		return academicRepository.findAll();
	}

	@Override
	public List<Academic> findByCriteria(String fullName) {
		// TODO Auto-generated method stubthePayment
		return null;
	}

	@Override
	public List<Academic> findByStudent(String fullName) {

		return academicRepository.findByStudent(fullName);
	}


	@Override
	public List<Academic> findByStudentAndGrade(String fullName, int gradeId) {

		return academicRepository.findByStudentAndGrade(fullName, gradeId);

	}

	@Override
	public Integer gradeStrength(int gradeId) {
		
		return academicRepository.gradeStrength(gradeId);
	}

	

	@Override
	public List<Academic> findByCategory(Category categoryTitle) {
		
		return academicRepository.findByCategory(categoryTitle);
	}


	@Override
	public Integer countByGradeIdBetweenAndCategory(int gradeStart, int gradeEnd, Category category) {
		
		return academicRepository.countByGradeIdBetweenAndCategory(gradeStart, gradeEnd, category);
	}

	@Override
	public Integer countByCategory(Category category) {
		
		return academicRepository.countByCategory(category);
	}
	
	@Override
	public Integer countByIsActive() {
		return academicRepository.countByIsActive(true);
	}

	@Override
	public Integer countByGradeIdAndCategory(int gradeId, Category category) {
		
		return academicRepository.countByGradeIdAndCategory(gradeId, category);
	}

	@Override
	public List<Academic> findByRecentAdmissions() {
		
		return academicRepository.findByRecentAdmission();
	}



}
