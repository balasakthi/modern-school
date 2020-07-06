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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.balsa.school.entity.Grade;
import com.balsa.school.service.GradeService;

@Controller
public class GradeController {

	private GradeService gradeService;

	private Logger logger = Logger.getLogger(getClass().getName());

	public GradeController(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	@RequestMapping("/listGrade")
	public String listGrade(Model theModel) {

		// get the grade from the database
		List<Grade> theGradeList = gradeService.findAll();

		// add to spring model
		theModel.addAttribute("gradeList", theGradeList);

		logger.info("showing the list of grade.");

		return "grade-list";

	}

	@GetMapping("/showGradeForm")
	public String showAddGradeForm(Model theModel) {

		logger.info("showing the form to add the new grade.");

		// create model attribute to bind form data
		Grade theGrade = new Grade();

		theModel.addAttribute("grade", theGrade);

		return "grade-form";
	}

	@PostMapping("/saveGrade")
	public String saveGrade(@Valid @ModelAttribute("grade") Grade theGrade, BindingResult theBindingResult,
			Model theModel) {

		logger.info("saving the grade.");

		if (theBindingResult.hasErrors()) {

			List<FieldError> errors = theBindingResult.getFieldErrors();
			for (FieldError error : errors) {

				logger.warning(error.getField() + " - " + error.getDefaultMessage());
			}

			return "grade-form";
		}

		try {

			// save the grade
			gradeService.save(theGrade);
			logger.info("grade saved.");

		} catch (Exception e) {
			e.printStackTrace();
		}
		// use a redirect to prevent duplicate submissions
		return "redirect:/listGrade";

	}

	@GetMapping("/updateGrade")
	public String showUpdateGradeForm(@RequestParam("gradeId") int theGradeId, Model theModel) {

		// get the grade from the Service
		Grade theGrade = gradeService.findById(theGradeId);

		// set Staff as a model attribute to pre-populate the form
		theModel.addAttribute("grade", theGrade);

		// send over to our form
		return "grade-form";

	}

	@GetMapping("/deleteGrade")
	public String deleteGrade(@RequestParam("gradeId") int theGradeId) {

		// delete the grade
		gradeService.deleteById(theGradeId);

		// redirect to grade list
		return "redirect:/listGrade";

	}

}
