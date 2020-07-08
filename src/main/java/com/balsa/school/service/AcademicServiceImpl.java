package com.balsa.school.service;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.balsa.school.dao.AcademicRepository;
import com.balsa.school.entity.Academic;

@Service
public class AcademicServiceImpl implements AcademicService {

	private AcademicRepository academicRepository;

	public AcademicServiceImpl(AcademicRepository academicRepository) {
		this.academicRepository = academicRepository;
	}

	@Override
	public List<Academic> findAll() {
		return academicRepository.findAll();
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
	public Integer getTotalNewAdmission() {
		return academicRepository.getTotalNewAdmission();
	}

	@Override
	public Integer getTotalStudents() {
		return academicRepository.countByIsActive(true);
	}

	@Override
	public Integer getTotalMatric() {
		return academicRepository.getTotalMatric();
	}

	@Override
	public Integer getTotalNewPrePrimary() {

		return academicRepository.getTotalNewPrePrimary();
	}

	@Override
	public Integer getTotalNewPrimary() {

		return academicRepository.getTotalNewPrimary();
	}

	@Override
	public Integer getTotalNewSecondary() {

		return academicRepository.getTotalNewSecondary();
	}

	@Override
	public Integer getTotalNewHigherSecondary() {

		return academicRepository.getTotalNewHigherSecondary();
	}

	@Override
	public Integer getTotalOldHigherSecondary() {
		return academicRepository.getTotalOldHigherSecondary();
	}

	@Override
	public Integer getTotalIntegratedA1() {

		return academicRepository.getTotalIntegratedA1();
	}

	@Override
	public Integer getTotalIntegratedB1() {

		return academicRepository.getTotalIntegratedB1();
	}

	@Override
	public Integer getTotalIntegratedB2() {

		return academicRepository.getTotalIntegratedB2();
	}

	@Override
	public Integer getTotalIntegratedC1() {

		return academicRepository.getTotalIntegratedC1();
	}

	@Override
	public Integer getTotalIntegratedC2() {

		return academicRepository.getTotalIntegratedC2();
	}

	@Override
	public Integer getTotalNonIntegratedA1() {

		return academicRepository.getTotalNonIntegratedA1();
	}

	@Override
	public Integer getTotalNonIntegratedB1() {

		return academicRepository.getTotalNonIntegratedB1();
	}

	@Override
	public Integer getTotalNonIntegratedB2() {

		return academicRepository.getTotalNonIntegratedB2();
	}

	@Override
	public Integer getTotalNonIntegratedC1() {

		return academicRepository.getTotalNonIntegratedC1();
	}

	@Override
	public Integer getTotalNonIntegratedC2() {

		return academicRepository.getTotalNonIntegratedC2();
	}

	@Override
	public void save(Academic theAcademic) {

		academicRepository.save(theAcademic);
	}

}
