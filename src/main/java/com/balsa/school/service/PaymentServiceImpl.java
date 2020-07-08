package com.balsa.school.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balsa.school.dao.PaymentRepository;
import com.balsa.school.entity.Academic;
import com.balsa.school.entity.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

	private PaymentRepository paymentRepository;

	@Autowired
	public PaymentServiceImpl(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	@Override
	public List<Payment> findAll() {
		return paymentRepository.findAll();
	}

	@Override
	public Payment findById(int thePaymentId) {
		Optional<Payment> result = paymentRepository.findById(thePaymentId);
		Payment thePayment = null;
		if (result.isPresent()) {
			thePayment = result.get();
		} else {
			throw new RuntimeException("Payment Not Found- " + thePaymentId);
		}
		return thePayment;
	}

	@Override
	public List<Payment> findByAcademic(Academic theAcademic) {

		return paymentRepository.findByAcademic(theAcademic);
	}

	@Override
	public Long sumPaidFeeById(int theAcademicId) {
		return paymentRepository.sumPaidFeeById(theAcademicId);
	}

	@Override
	public Double sumAllPaidFee() {
		return paymentRepository.sumAllPaidFee();
	}

	@Override
	public void save(Payment thePayment) {
		paymentRepository.save(thePayment);

	}

	@Override
	public void deleteById(int thePaymentId) {
		paymentRepository.deleteById(thePaymentId);

	}

	@Override
	public List<Payment> findByPayDate(Date date) {
		return paymentRepository.findByPayDate(date);
	}

	@Override
	public List<Payment> findByMaxPayDate() {
		
		return paymentRepository.findByMaxPayDate();
	}

}
