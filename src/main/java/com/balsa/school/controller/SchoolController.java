package com.balsa.school.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.hibernate.boot.archive.scan.spi.ClassDescriptor.Categorization;
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
import com.balsa.school.entity.Category;
import com.balsa.school.entity.Grade;
import com.balsa.school.entity.Payment;
import com.balsa.school.entity.Student;
import com.balsa.school.service.AcademicService;
import com.balsa.school.service.GradeService;
import com.balsa.school.service.PaymentService;
import com.balsa.school.service.StudentService;
import com.balsa.school.exporter.ExcelFileExporter;

@Controller
public class SchoolController {

	private Logger logger = Logger.getLogger(getClass().getName());

	private AcademicService academicService;
	private PaymentService paymentService;
	private StudentService studentService;
	private GradeService gradeService;
	private Category category;

	public SchoolController(AcademicService academicService, PaymentService paymentService,
			StudentService studentService, GradeService gradeService) {
		this.academicService = academicService;
		this.paymentService = paymentService;
		this.studentService = studentService;
		this.gradeService = gradeService;
	}

	@RequestMapping("/students-list")
	public String listStudents(Model theModel) {
		List<Academic> theStudents = academicService.findAll();
		List<Grade> theGrades = gradeService.findAll();
		theModel.addAttribute("studentList", theStudents);
		theModel.addAttribute("gradeList", theGrades);
		return "student-list";
	}

	@GetMapping("/student-form")
	public String showStudentForm(Model theModel) {
		Student theStudent = new Student();
		Academic theAcademic=new Academic();
		List<Grade> theGrades=gradeService.findAll();
		theModel.addAttribute("student", theStudent);
		theModel.addAttribute("academic", theAcademic);
		theModel.addAttribute("gradeList", theGrades);
		return "student-form";
	}

//	@PostMapping("/save-student")
//	public String saveStudent(@Valid @ModelAttribute("student") Student theStudent, BindingResult theBindingResult,
//			Model theModel) {
//		logger.info("saving the student for- " + theStudent.getFullName());
//		if (theBindingResult.hasErrors()) {
//			List<FieldError> errors = theBindingResult.getFieldErrors();
//			for (FieldError error : errors) {
//				logger.warning(error.getField() + " - " + error.getDefaultMessage());
//			}
//			return "student-form";
//		}
//		try {
//			// save the student
//			studentService.save(theStudent);
//			logger.info("student saved.");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// use a redirect to prevent duplicate submissions
//		return "redirect:/students-list";
//	}
	
	@PostMapping("/save-student")
	public String saveStudent(@Valid @ModelAttribute("academic") Academic theAcademic, BindingResult theBindingResult,
			Model theModel) {
		logger.info("saving the student for- " + theAcademic);
		
		theAcademic.setIsActive(true);
		theAcademic.setCategory(category.NEW);
		
		if (theBindingResult.hasErrors()) {
			List<FieldError> errors = theBindingResult.getFieldErrors();
			for (FieldError error : errors) {
				logger.warning(error.getField() + " - " + error.getDefaultMessage());
			}
			return "student-form";
		}
		try {
			// save the academic
			academicService.save(theAcademic);
			logger.info("student saved.");

		} catch (Exception e) {
			e.printStackTrace();
		}
		// use a redirect to prevent duplicate submissions
		return "redirect:/students-list";
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

	@GetMapping("/export")
	public void downloadCsv(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=students.xlsx");
		ByteArrayInputStream stream = ExcelFileExporter.studentListToExcelFile(createTestData());
		IOUtils.copy(stream, response.getOutputStream());
	}

	private List<Academic> createTestData() {
		List<Academic> theStudents = academicService.findAll();
		return theStudents;
	}

}
