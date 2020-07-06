package com.balsa.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.balsa.school.dao.GradeRepository;
import com.balsa.school.entity.Grade;

@Service
public class GradeServiceImpl implements GradeService {

	private GradeRepository gradeRepository;

	public GradeServiceImpl(GradeRepository gradeRepository) {
		this.gradeRepository = gradeRepository;
	}

	@Override
	public List<Grade> findAll() {

		return gradeRepository.findAll();
	}

	@Override
	public Grade findById(int theGradeId) {
		Optional<Grade> result = gradeRepository.findById(theGradeId);
		Grade theGrade = null;
		if (result.isPresent()) {
			theGrade = result.get();
		} else {
			throw new RuntimeException("Grade Not Found- " + theGradeId);
		}
		return theGrade;
	}

	@Override
	public void save(Grade theGrade) {
		gradeRepository.save(theGrade);

	}

	@Override
	public void deleteById(int theGradeId) {
		gradeRepository.deleteById(theGradeId);

	}

}
