package com.balsa.school.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.balsa.school.entity.Academic;
import com.balsa.school.entity.Grade;
import com.balsa.school.entity.Payment;
import com.balsa.school.entity.Student;
import com.balsa.school.service.AcademicService;
import com.balsa.school.service.GradeService;
import com.balsa.school.service.PaymentService;
import com.balsa.school.service.StudentService;

@Controller
public class PaymentController {

	private Logger logger = Logger.getLogger(getClass().getName());

	private AcademicService academicService;
	private PaymentService paymentService;
	private StudentService studentService;
	private GradeService gradeService;

	public PaymentController(AcademicService academicService, PaymentService paymentService,
			StudentService studentService, GradeService gradeService) {
		this.academicService = academicService;
		this.paymentService = paymentService;
		this.studentService = studentService;
		this.gradeService = gradeService;
	}
	
	@GetMapping("/payments-find")
	public String initFindForm(Model theModel) {
		
		List<Academic> theAcademicList = academicService.findAll();
		
		List<Grade> theGrades = gradeService.findAll();
		
		theModel.addAttribute("grade", theGrades);
		theModel.addAttribute("academic", theAcademicList);
		theModel.addAttribute("payment", new Payment());
		
		return "find-payments";
	}
	
	@GetMapping("/payments")
	public String processFindForm(Payment thePayment, BindingResult theResult, Model theModel) {

		logger.info("finding the payments : "+thePayment.getPayDate());
		
		List<Payment> thePayments;
		
		if(thePayment.getPayDate()!=null) {
			thePayments=paymentService.findByMaxPayDate();
		}
		
		thePayments=paymentService.findAll();

		theModel.addAttribute("paymentsList", thePayments);

		return "payments-list";
	}

	
	@GetMapping("/payment-form")
	public String showPaymentForm(Model theModel) {
		
		List<Academic> theAcademicList = academicService.findAll();

		Payment thePayment = new Payment();

		theModel.addAttribute("academic", theAcademicList);
		theModel.addAttribute("payment", thePayment);
		
		return "payment-form";
	}
	
	@PostMapping("/save-payment")
	public String savePayment(@Valid @ModelAttribute("payment") Payment thePayment, BindingResult theBindingResult,
			Model theModel) {

		logger.info("saving the payment for- " + thePayment.getAcademic().getStudent().getFullName());

		if (theBindingResult.hasErrors()) {

			List<FieldError> errors = theBindingResult.getFieldErrors();
			for (FieldError error : errors) {

				logger.warning(error.getField() + " - " + error.getDefaultMessage());
			}

			theModel.addAttribute("academic", thePayment.getAcademic());

			return "payment-form";
		}

		try {

			// save the payment
			paymentService.save(thePayment);
			logger.info("payment saved.");

		} catch (Exception e) {
			e.printStackTrace();
		}
		// use a redirect to prevent duplicate submissions
		return "redirect:/student-list";

	}
	
	
	@GetMapping("/payment-foddrm7")
	public String showPaymendtForm(@RequestParam(value = "academic-id", required = false, defaultValue="0") int theAcademicId,
			Model theModel) {

		if (theAcademicId != 0) {

			logger.info("showing the payment form");

			Academic theAcademicById = academicService.findById(theAcademicId);

			theModel.addAttribute("academic", theAcademicById);

		} else {
			List<Academic> theAcademicList = academicService.findAll();

			theModel.addAttribute("academic", theAcademicList);
		}

		Payment thePayment = new Payment();

		theModel.addAttribute("payment", thePayment);

		return "payment-form";
	}

	

}
