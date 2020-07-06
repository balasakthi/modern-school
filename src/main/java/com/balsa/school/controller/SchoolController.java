package com.balsa.school.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.balsa.school.entity.Academic;
import com.balsa.school.entity.Grade;
import com.balsa.school.entity.Payment;
import com.balsa.school.service.AcademicService;
import com.balsa.school.service.GradeService;
import com.balsa.school.service.PaymentService;
import com.balsa.school.exporter.ExcelFileExporter;

@Controller
public class SchoolController {

	private AcademicService academicService;
	private PaymentService paymentService;
	private GradeService gradeService;

	private Logger logger = Logger.getLogger(getClass().getName());

	public SchoolController(AcademicService academicService, PaymentService paymentService, GradeService gradeService) {
		this.academicService = academicService;
		this.paymentService = paymentService;
		this.gradeService = gradeService;
	}

	@GetMapping("/export")
	public void downloadCsv(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=students.xlsx");
		ByteArrayInputStream stream = ExcelFileExporter.studentListToExcelFile(createTestData());
		IOUtils.copy(stream, response.getOutputStream());
	}
	
	private List<Academic> createTestData(){
		List<Academic> theStudents = academicService.findAll();
		return theStudents;
	}

	

	@RequestMapping("/students-list")
	public String listStudents(Model theModel) {
		List<Academic> theStudents = academicService.findAll();
		List<Grade> theGrades = gradeService.findAll();
		theModel.addAttribute("studentList", theStudents);
		theModel.addAttribute("gradeList", theGrades);
		return "student-list";

	}

	@GetMapping("/student")
	public String showStudentPayments(@RequestParam("academic-id") int theAcademicId, Model theModel) {
		Academic theAcademic = academicService.findById(theAcademicId);
		List<Payment> thePayment = paymentService.findByAcademic(theAcademic);
		Long totalPaidFee = paymentService.sumPaidFeeById(theAcademicId);
		theModel.addAttribute("payment", thePayment);
		theModel.addAttribute("totalFee", totalPaidFee);
		return "student-payment";
	}

	@GetMapping("/payment")
	public String showPaymentForm(@RequestParam("academic-id") int theAcademicId, Model theModel) {

		logger.info("showing new payment form");

		Academic theAcademic = academicService.findById(theAcademicId);

		Payment thePayment = new Payment();

		theModel.addAttribute("academic", theAcademic);

		theModel.addAttribute("payment", thePayment);

		return "payment-form";
	}

	@PostMapping("/savePayment")
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
		return "redirect:/students-list";

	}

}
