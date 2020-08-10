package com.balsa.school.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.balsa.school.entity.Academic;
import com.balsa.school.entity.Category;
import com.balsa.school.entity.Grade;
import com.balsa.school.entity.Payment;
import com.balsa.school.entity.Student;
import com.balsa.school.exporter.ExcelFileExporter;
import com.balsa.school.service.AcademicService;
import com.balsa.school.service.GradeService;
import com.balsa.school.service.PaymentService;
import com.balsa.school.service.StudentService;


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

	@GetMapping("/students-find")
	public String initFindForm(Model theModel) {
		
		List<Grade> theGrades = gradeService.findAll();
		
		theModel.addAttribute("grade", theGrades);
		theModel.addAttribute("payment", new Payment());
		
		return "find-students";
	}

	@GetMapping("/students")
	public String processFindForm(Payment thePayment, BindingResult theResult, Model theModel) {

		logger.info("finding the students");
		
		List<Academic> theStudents;

		String fullName = thePayment.getAcademic().getStudent().getFullName();

		if (fullName == null) {

			thePayment.getAcademic().getStudent().setFullName("");
		}

		if (thePayment.getAcademic().getGrade() != null) {

			theStudents = academicService.findByStudentAndGrade(fullName,
					thePayment.getAcademic().getGrade().getId());

		} else {

			theStudents = academicService.findByStudent(fullName);

		}

		List<Grade> theGrades = gradeService.findAll();
		theModel.addAttribute("studentList", theStudents);
		theModel.addAttribute("grade", theGrades);

		return "students-list";
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

	@GetMapping("/student-form")
	public String showStudentForm(Model theModel) {
		
		Student theStudent = new Student();
		
		Academic theAcademic = new Academic();
		
		List<Grade> theGrades = gradeService.findAll();
		
		theModel.addAttribute("student", theStudent);
		theModel.addAttribute("academic", theAcademic);
		theModel.addAttribute("gradeList", theGrades);
		
		return "student-form";
	}

	@GetMapping("/update-student")
	public String updateStudent(@RequestParam("academic-id") int theAcademicId, Model theModel) {
		
		Academic theAcademic= academicService.findById(theAcademicId);
		
		List<Grade> theGrades = gradeService.findAll();
		
		theModel.addAttribute("academic", theAcademic);
		theModel.addAttribute("gradeList", theGrades);
		
		return "student-form";
	}
	
	
	@PostMapping("/save-student")
	public String saveStudent(@Valid @ModelAttribute("academic") Academic theAcademic, BindingResult theBindingResult,
			Model theModel) {
		
		logger.info("saving the student for- " + theAcademic);
		
		theAcademic.setIsActive(true);
		theAcademic.setCategory(theAcademic.getCategory());

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
		return "redirect:/students-find";
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
