package com.balsa.school.service;

import java.util.List;

import com.balsa.school.entity.Grade;

public interface GradeService {

	public List<Grade> findAll();

	public Grade findById(int theGradeId);

	public void save(Grade theGrade);

	public void deleteById(int theGradeId);

}
