package com.balsa.school.service;

import java.util.List;

import com.balsa.school.entity.Academic;
import com.balsa.school.entity.Student;

public interface AcademicService {
	
	public void save(Academic theAcademic);

	public Integer getTotalNewAdmission();

	public Integer getTotalStudents();

	public Integer getTotalMatric();

	public Integer getTotalNewPrePrimary();

	public Integer getTotalNewPrimary();

	public Integer getTotalNewSecondary();

	public Integer getTotalNewHigherSecondary();

	public Integer getTotalOldHigherSecondary();

	public List<Academic> findAll();

	public Academic findById(int theAcademicId);

	
	// methods to get strength of Integrated XI grades

	public Integer getTotalIntegratedA1();

	public Integer getTotalIntegratedB1();

	public Integer getTotalIntegratedB2();

	public Integer getTotalIntegratedC1();

	public Integer getTotalIntegratedC2();

	
	// methods to get strength of Non Integrated XI grades
	
	public Integer getTotalNonIntegratedA1();

	public Integer getTotalNonIntegratedB1();

	public Integer getTotalNonIntegratedB2();

	public Integer getTotalNonIntegratedC1();

	public Integer getTotalNonIntegratedC2();

}
