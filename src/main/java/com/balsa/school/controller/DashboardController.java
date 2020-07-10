package com.balsa.school.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.balsa.school.bean.AdmissionStrength;
import com.balsa.school.bean.HigherSecondaryStrength;
import com.balsa.school.entity.Payment;
import com.balsa.school.service.AcademicService;
import com.balsa.school.service.PaymentService;

@Controller
public class DashboardController {

	private AcademicService academicService;
	private PaymentService paymentService;
	private AdmissionStrength admissionStrength;
	private HigherSecondaryStrength higherSecondaryStrength;

	private Logger logger = Logger.getLogger(getClass().getName());

	private Double totalPaidFee;

	public DashboardController(AcademicService academicService, PaymentService paymentService) {
		this.academicService = academicService;
		this.paymentService = paymentService;

	}

	// method to find the total new admission
	public void findAcademicStrength() {
		admissionStrength = new AdmissionStrength();
		admissionStrength.setTotalAdmission(academicService.getTotalNewAdmission());
		admissionStrength.setTotalStudents(academicService.getTotalStudents());
		admissionStrength.setTotalMatric(academicService.getTotalMatric());
		admissionStrength.setTotalNewPrePrimary(academicService.getTotalNewPrePrimary());
		admissionStrength.setTotalNewPrimary(academicService.getTotalNewPrimary());
		admissionStrength.setTotalNewSecondary(academicService.getTotalNewSecondary());
		admissionStrength.setTotalNewHigherSecondary(academicService.getTotalNewHigherSecondary());
		admissionStrength.setTotalOldHigherSecondary(academicService.getTotalOldHigherSecondary());
	}

	// method to find the higher secondary strength
	public void findHigherSecondaryStrength() {
		higherSecondaryStrength = new HigherSecondaryStrength();
		higherSecondaryStrength.setTotalIntegratedA1(academicService.getTotalIntegratedA1());
		higherSecondaryStrength.setTotalIntegratedB1(academicService.getTotalIntegratedB1());
		higherSecondaryStrength.setTotalIntegratedB2(academicService.getTotalIntegratedB2());
		higherSecondaryStrength.setTotalIntegratedC1(academicService.getTotalIntegratedC1());
		higherSecondaryStrength.setTotalIntegratedC2(academicService.getTotalIntegratedC2());
		higherSecondaryStrength.setTotalNonIntegratedA1(academicService.getTotalNonIntegratedA1());
		higherSecondaryStrength.setTotalNonIntegratedB1(academicService.getTotalNonIntegratedB1());
		higherSecondaryStrength.setTotalNonIntegratedB2(academicService.getTotalNonIntegratedB2());
		higherSecondaryStrength.setTotalNonIntegratedC1(academicService.getTotalNonIntegratedC1());
		higherSecondaryStrength.setTotalNonIntegratedC2(academicService.getTotalNonIntegratedC2());
	}

	// shows the dashboard page
	@RequestMapping("/")
	public String showDashboard(Model theModel) {

		// logger.info("showing dashboard");

		// gets the total new admissions
		findAcademicStrength();

		// gets the total higher secondary
		findHigherSecondaryStrength();

		totalPaidFee = paymentService.sumAllPaidFee();

		List<Payment> theRecentPayments = paymentService.findByMaxPayDate();

		theModel.addAttribute("recentPayments", theRecentPayments);
		theModel.addAttribute("admissionStrength", admissionStrength);
		theModel.addAttribute("higherSecondary", higherSecondaryStrength);
		theModel.addAttribute("totalPaidFee", totalPaidFee);
		theModel.addAttribute("todaysDate", LocalDateTime.now());

		return "dashboard";
	}
}
