package com.balsa.school.service;

import java.util.Date;
import java.util.List;

import com.balsa.school.dao.specs.AcademicSpecification;
import com.balsa.school.entity.Academic;
import com.balsa.school.entity.Category;
import com.balsa.school.entity.Grade;
import com.balsa.school.entity.Student;

public interface AcademicService {

	public void save(Academic theAcademic);


	public List<Academic> findAll();

	public Academic findById(int theAcademicId);

	
	// method to get the student list based on payment dates

	public List<Academic> findByPayDate(Date thePayDate);

	public List<Academic> findByMaxPayDate();

	public List<Academic> findAll(AcademicSpecification academicSpecification);	
	
	public List<Academic> findByCriteria(String fullName, List<Integer> gradeId);
	
	public List<Academic> findByCriteria(String fullName);
	
	
	
	public Integer countByIsActive();
	
	public Integer countByCategory(Category category);
	
	public Integer gradeStrength(int gradeId);
	
	public Integer countByGradeIdBetweenAndCategory(int gradeStart, int gradeEnd, Category category);
	
	public Integer countByGradeIdAndCategory(int gradeId, Category category);
	
	public List<Academic> findByCategory(Category categoryTitle);	
	
	// Finding students
	public List<Academic> findByStudentAndGrade(String fullName, int gradeId);
	
	public List<Academic> findByStudent(String fullName);
	

}
