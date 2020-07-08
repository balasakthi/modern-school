package com.balsa.school.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.balsa.school.entity.Academic;

public interface AcademicRepository extends JpaRepository<Academic, Integer> {

	Integer countByIsActive(Boolean isActive);

	List<Academic> findByGradeIn(List<String> grades);
	
	@Query(value = "SELECT count(id) FROM academic WHERE category IN ('MATRIC')", nativeQuery = true)
	Integer getTotalMatric();

	@Query(value = "SELECT count(id) FROM academic WHERE category IN ('NEW','MATRIC')", nativeQuery = true)
	Integer getTotalNewAdmission();

	// Queries to get the total new admissions
	@Query(value = "SELECT count(id) FROM academic WHERE grade_id IN (1,2,3) and category IN ('NEW', 'MATRIC')", nativeQuery = true)
	Integer getTotalNewPrePrimary();

	@Query(value = "SELECT count(id) FROM academic WHERE grade_id BETWEEN 4 AND 8 and category IN ('NEW', 'MATRIC')", nativeQuery = true)
	Integer getTotalNewPrimary();

	@Query(value = "SELECT count(id) FROM academic WHERE grade_id BETWEEN 9 AND 13 and category IN ('NEW', 'MATRIC')", nativeQuery = true)
	Integer getTotalNewSecondary();

	@Query(value = "SELECT count(id) FROM academic WHERE grade_id BETWEEN 14 AND 23 and category IN ('NEW')", nativeQuery = true)
	Integer getTotalNewHigherSecondary();

	@Query(value = "SELECT count(id) FROM academic WHERE grade_id BETWEEN 14 AND 23 and category IN ('OLD')", nativeQuery = true)
	Integer getTotalOldHigherSecondary();

	// Queries to get strength of a integrated higher secondary class
	@Query(value = "SELECT count(id) FROM academic WHERE grade_id IN (14)", nativeQuery = true)
	Integer getTotalIntegratedA1();
	
	@Query(value = "SELECT count(id) FROM academic WHERE grade_id IN (15)", nativeQuery = true)
	Integer getTotalIntegratedB1();
	
	@Query(value = "SELECT count(id) FROM academic WHERE grade_id IN (16)", nativeQuery = true)
	Integer getTotalIntegratedB2();
	
	@Query(value = "SELECT count(id) FROM academic WHERE grade_id IN (17)", nativeQuery = true)
	Integer getTotalIntegratedC1();
	
	@Query(value = "SELECT count(id) FROM academic WHERE grade_id IN (18)", nativeQuery = true)
	Integer getTotalIntegratedC2();
	
	// Queries to get strength of a non integrated higher secondary class
	@Query(value = "SELECT count(id) FROM academic WHERE grade_id IN (19)", nativeQuery = true)
	Integer getTotalNonIntegratedA1();
	
	@Query(value = "SELECT count(id) FROM academic WHERE grade_id IN (20)", nativeQuery = true)
	Integer getTotalNonIntegratedB1();
	
	@Query(value = "SELECT count(id) FROM academic WHERE grade_id IN (21)", nativeQuery = true)
	Integer getTotalNonIntegratedB2();
	
	@Query(value = "SELECT count(id) FROM academic WHERE grade_id IN (22)", nativeQuery = true)
	Integer getTotalNonIntegratedC1();
	
	@Query(value = "SELECT count(id) FROM academic WHERE grade_id IN (23)", nativeQuery = true)
	Integer getTotalNonIntegratedC2();
	

}
