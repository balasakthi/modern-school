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
	
	@GetMapping("/payment-form")
	public String showPaymentForm(Model theModel) {

		List<Academic> theAcademicList = academicService.findAll();

		Payment thePayment = new Payment();

		theModel.addAttribute("academic", theAcademicList);
		theModel.addAttribute("payment", thePayment);

		return "payment-form";
	}

	@GetMapping("/payments")
	public String processFindForm(Payment thePayment, BindingResult theResult, Model theModel) {

		logger.info("finding the payments : " + thePayment.getPayDate());

		List<Payment> thePayments;

		if (thePayment.getPayDate() != null)
			thePayments = paymentService.findByPayDate(thePayment.getPayDate());
		else
			thePayments = paymentService.findAll();

		theModel.addAttribute("paymentsList", thePayments);

		return "payments-list";
	}

	@GetMapping("/students-payment")
	public String sudentPayment(@RequestParam("academic-id") int theAcademicId, Model theModel) {

		Academic theAcademic = academicService.findById(theAcademicId);

		Payment thePayment = new Payment();

		theModel.addAttribute("academic", theAcademic);
		theModel.addAttribute("payment", thePayment);

		return "payment-form";
	}
	
	
	@GetMapping("/update-payment")
	public String updatePayment(@RequestParam("payment-id") int thePaymentId, Model theModel) {
		
		List<Academic> theAcademicList = academicService.findAll();

		Payment thePayment = paymentService.findById(thePaymentId);

		theModel.addAttribute("payment", thePayment);
		theModel.addAttribute("academic", theAcademicList);

		return "payment-form";
	}
	
	@GetMapping("/delete-payment")
	public String deletePayment(@RequestParam("payment-id") int thePaymentId) {
		

		paymentService.deleteById(thePaymentId);

		return "redirect:/payments-find";
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
		return "redirect:/payments-find";

	}


}
