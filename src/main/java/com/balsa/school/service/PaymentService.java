package com.balsa.school.service;

import java.util.Date;
import java.util.List;

import com.balsa.school.entity.Academic;
import com.balsa.school.entity.Payment;

public interface PaymentService {

	public List<Payment> findAll();

	public Payment findById(int paymentId);

	public void save(Payment thePayment);

	public void deleteById(int thePaymentId);

	public List<Payment> findByAcademic(Academic theAcademic);

	public Long sumPaidFeeById(int theAcademicId);

	public Long sumAllPaidFee();
	
	public List<Payment> findByPayDate(Date date);
	
	public List<Payment> findByMaxPayDate();

}
