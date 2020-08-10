package com.balsa.school.bean;

import java.util.LinkedHashMap;
import java.util.Map;
import com.balsa.school.entity.Category;
import com.balsa.school.service.AcademicService;

public class ChartMap {

	private AcademicService academicService;

	public ChartMap(AcademicService academicService) {
		this.academicService = academicService;
	}

	// returns the total available students
	public Integer studentCount() {
		return academicService.countByIsActive();
	}

	// returns the total admissions
	public Integer admissionCount() {

		Map<String, Integer> admission = newAdmission();

		Integer admissionTotal = 0;
		for (Integer count : admission.values()) {
			admissionTotal += count;
		}

		return admissionTotal;
	}

	// Map for the new admission chart
	public Map<String, Integer> newAdmission() {
		Map<String, Integer> admission = new LinkedHashMap<>();
		admission.put("Pre Primary", academicService.countByGradeIdBetweenAndCategory(1, 3, Category.NEW)
				+ academicService.countByGradeIdBetweenAndCategory(1, 3, Category.MATRIC));
		admission.put("Primary", academicService.countByGradeIdBetweenAndCategory(4, 8, Category.NEW)
				+ academicService.countByGradeIdBetweenAndCategory(4, 8, Category.MATRIC));
		admission.put("Secondary", academicService.countByGradeIdBetweenAndCategory(9, 13, Category.NEW)
				+ academicService.countByGradeIdBetweenAndCategory(9, 13, Category.MATRIC));
		admission.put("Higher Secondary", academicService.countByGradeIdBetweenAndCategory(14, 23, Category.NEW)
				+ academicService.countByGradeIdBetweenAndCategory(14, 23, Category.PROMOTED));
		return admission;
	}

	// Map for the higher secondary chart
	public Map<String, Integer> higherSecondaryIntegrated() {
		Map<String, Integer> integratedHigher = new LinkedHashMap<>();
		integratedHigher.put("XI A1", academicService.gradeStrength(14));
		integratedHigher.put("XI B1", academicService.gradeStrength(15));
		integratedHigher.put("XI B2", academicService.gradeStrength(16));
		integratedHigher.put("XI C1", academicService.gradeStrength(17));
		integratedHigher.put("XI C2", academicService.gradeStrength(18));
		return integratedHigher;
	}

	// Map for the higher secondary chart
	public Map<String, Integer> higherSecondaryNonIntegrated() {
		Map<String, Integer> nonIntegratedHigher = new LinkedHashMap<>();
		nonIntegratedHigher.put("XI A1", academicService.gradeStrength(19));
		nonIntegratedHigher.put("XI B1", academicService.gradeStrength(20));
		nonIntegratedHigher.put("XI B2", academicService.gradeStrength(21));
		nonIntegratedHigher.put("XI C1", academicService.gradeStrength(22));
		nonIntegratedHigher.put("XI C2", academicService.gradeStrength(23));
		return nonIntegratedHigher;
	}

	// Map for the new grade strength chart
	public Map<String, Integer> newAdmissionStrength() {
		Map<String, Integer> admissionStrength = new LinkedHashMap<>();
		admissionStrength.put("PreKG", gradeStrengthByCategory(Grades.PreKG, Category.NEW));
		admissionStrength.put("LKG",
				gradeStrengthByCategory(Grades.LKG, Category.NEW) + gradeStrengthByCategory(Grades.LKG, Category.MATRIC));
		admissionStrength.put("UKG",
				gradeStrengthByCategory(Grades.UKG, Category.NEW) + gradeStrengthByCategory(Grades.UKG, Category.MATRIC));
		admissionStrength.put("I", gradeStrengthByCategory(Grades.I, Category.NEW) + gradeStrengthByCategory(Grades.I, Category.MATRIC));
		admissionStrength.put("II",
				gradeStrengthByCategory(Grades.II, Category.NEW) + gradeStrengthByCategory(Grades.II, Category.MATRIC));
		admissionStrength.put("III",
				gradeStrengthByCategory(Grades.III, Category.NEW) + gradeStrengthByCategory(Grades.III, Category.MATRIC));
		admissionStrength.put("IV",
				gradeStrengthByCategory(Grades.IV, Category.NEW) + gradeStrengthByCategory(Grades.IV, Category.MATRIC));
		admissionStrength.put("V", gradeStrengthByCategory(Grades.V, Category.NEW) + gradeStrengthByCategory(Grades.V, Category.MATRIC));
		admissionStrength.put("VI",
				gradeStrengthByCategory(Grades.VI, Category.NEW) + gradeStrengthByCategory(Grades.VI, Category.MATRIC));
		admissionStrength.put("VII",
				gradeStrengthByCategory(Grades.VII, Category.NEW) + gradeStrengthByCategory(Grades.VII, Category.MATRIC));
		admissionStrength.put("VIII",
				gradeStrengthByCategory(Grades.VIII, Category.NEW) + gradeStrengthByCategory(Grades.VIII, Category.MATRIC));
		admissionStrength.put("IX",
				gradeStrengthByCategory(Grades.IX, Category.NEW) + gradeStrengthByCategory(Grades.IX, Category.MATRIC));
		admissionStrength.put("X", gradeStrengthByCategory(Grades.X, Category.NEW) + gradeStrengthByCategory(Grades.X, Category.MATRIC));
		return admissionStrength;
	}

	// Map for the new grade strength chart
	public Map<String, Integer> promotedGradeStrength() {
		Map<String, Integer> promotedGrade = new LinkedHashMap<>();
		promotedGrade.put("PreKG", gradeStrengthByCategory(Grades.PreKG, Category.PROMOTED));
		promotedGrade.put("LKG", gradeStrengthByCategory(Grades.LKG, Category.PROMOTED));
		promotedGrade.put("UKG", gradeStrengthByCategory(Grades.UKG, Category.PROMOTED));
		promotedGrade.put("I", gradeStrengthByCategory(Grades.I, Category.PROMOTED));
		promotedGrade.put("II", gradeStrengthByCategory(Grades.II, Category.PROMOTED));
		promotedGrade.put("III", gradeStrengthByCategory(Grades.III, Category.PROMOTED));
		promotedGrade.put("IV", gradeStrengthByCategory(Grades.IV, Category.PROMOTED));
		promotedGrade.put("V", gradeStrengthByCategory(Grades.V, Category.PROMOTED));
		promotedGrade.put("VI", gradeStrengthByCategory(Grades.VI, Category.PROMOTED));
		promotedGrade.put("VII", gradeStrengthByCategory(Grades.VII, Category.PROMOTED));
		promotedGrade.put("VIII", gradeStrengthByCategory(Grades.VIII, Category.PROMOTED));
		promotedGrade.put("IX", gradeStrengthByCategory(Grades.IX, Category.PROMOTED));
		promotedGrade.put("X", gradeStrengthByCategory(Grades.X, Category.PROMOTED));
		return promotedGrade;
	}

	// finding strength in grade
	public Integer gradeStrengthByCategory(Grades grades, Category category) {

		Integer gradeStrength = null;

		switch (grades) {
		case PreKG:
			gradeStrength = academicService.countByGradeIdAndCategory(1, category);
			break;
		case LKG:
			gradeStrength = academicService.countByGradeIdAndCategory(2, category);
			break;
		case UKG:
			gradeStrength = academicService.countByGradeIdAndCategory(3, category);
			break;
		case I:
			gradeStrength = academicService.countByGradeIdAndCategory(4, category);
			break;
		case II:
			gradeStrength = academicService.countByGradeIdAndCategory(5, category);
			break;
		case III:
			gradeStrength = academicService.countByGradeIdAndCategory(6, category);
			break;
		case IV:
			gradeStrength = academicService.countByGradeIdAndCategory(7, category);
			break;
		case V:
			gradeStrength = academicService.countByGradeIdAndCategory(8, category);
			break;
		case VI:
			gradeStrength = academicService.countByGradeIdAndCategory(9, category);
			break;
		case VII:
			gradeStrength = academicService.countByGradeIdAndCategory(10, category);
			break;
		case VIII:
			gradeStrength = academicService.countByGradeIdAndCategory(11, category);
			break;
		case IX:
			gradeStrength = academicService.countByGradeIdAndCategory(12, category);
			break;
		case X:
			gradeStrength = academicService.countByGradeIdAndCategory(13, category);
			break;
		default:
			break;
		}
		return gradeStrength;
	}

}
