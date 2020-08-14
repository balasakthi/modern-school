package com.balsa.school.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.balsa.school.entity.Academic;
import com.balsa.school.entity.Category;

public interface AcademicRepository extends JpaRepository<Academic, Integer>, JpaSpecificationExecutor<Academic> {

	Integer countByIsActive(Boolean isActive);
	
	Integer countByCategory(Category category);
	
	// Query to find the strength of the given grade
	@Query(value = "SELECT count(id) FROM academic WHERE grade_id =:gradeId", nativeQuery = true)
	Integer gradeStrength(@Param("gradeId") int gradeId);
	
	Integer countByGradeIdBetweenAndCategory(int gradeStart,int gradeEnd, Category category);

	Integer countByGradeIdAndCategory(int gradeId, Category category);
	
	// Query to find students based on category (new, matric, old)
	List<Academic> findByCategory(Category category);

	// Queries to find the students based on name, grade
	@Query(value = "SELECT * FROM academic a WHERE a.student_id IN (SELECT (id) FROM student s WHERE s.full_name LIKE UPPER(CONCAT('%',:fullName,'%')))", nativeQuery = true)
	List<Academic> findByStudent(@Param("fullName") String fullName);

	@Query(value = "SELECT * FROM academic a WHERE a.student_id IN (SELECT (id) FROM student s WHERE s.full_name LIKE UPPER(CONCAT('%',:fullName,'%'))) AND a.grade_id IN (SELECT (id) FROM grade g WHERE g.id= :gradeId)", nativeQuery = true)
	List<Academic> findByStudentAndGrade(@Param("fullName") String fullName, @Param("gradeId") int gradeId);

	@Query(value= "SELECT * FROM academic a WHERE a.category IN ('NEW', 'MATRIC') ORDER BY id DESC LIMIT 8", nativeQuery=true)
	List<Academic> findByRecentAdmission();
	
}
