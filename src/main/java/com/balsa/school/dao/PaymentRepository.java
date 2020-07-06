package com.balsa.school.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.balsa.school.entity.Academic;
import com.balsa.school.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	// gets the total fee paid by a student by the academic id
	@Query(value = "SELECT sum(amount) from payment pay WHERE pay.academic_id = ?1", nativeQuery = true)
	Long sumPaidFeeById(int theAcademicId);

	// gets the total fee paid by all the students
	@Query(value = "SELECT sum(amount) from payment pay", nativeQuery = true)
	Long sumAllPaidFee();

	// list the payments by the recent date
	@Query(value = "SELECT * from payment WHERE pay_date = (SELECT MAX(pay_date) from payment)", nativeQuery = true)
	List<Payment> findByMaxPayDate();

	// list the payments by a student
	List<Payment> findByAcademic(Academic theAcademic);

	// list the payments by the given date
	List<Payment> findByPayDate(Date date);

}
