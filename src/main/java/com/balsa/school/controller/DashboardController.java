package com.balsa.school.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.balsa.school.bean.ChartMap;
import com.balsa.school.entity.Payment;
import com.balsa.school.service.AcademicService;
import com.balsa.school.service.PaymentService;

@Controller
public class DashboardController {

	private AcademicService academicService;
	private PaymentService paymentService;
	private ChartMap chartMap;


	private Double totalPaidFee;

	public DashboardController(AcademicService academicService, PaymentService paymentService) {
		this.academicService = academicService;
		this.paymentService = paymentService;
	}

	
	// shows the dashboard page
	@RequestMapping("/")
	public String showDashboard(Model theModel) {

		chartMap=new ChartMap(academicService);

		totalPaidFee = paymentService.sumAllPaidFee();

		List<Payment> theRecentPayments = paymentService.findByMaxPayDate();
		List<Payment> theRecentAdmissions = paymentService.findByRecentAdmission();

		theModel.addAttribute("recentPayments", theRecentPayments);
		theModel.addAttribute("recentAdmissions", theRecentAdmissions);
		
		theModel.addAttribute("totalPaidFee", totalPaidFee);
		
		theModel.addAttribute("todaysDate", LocalDateTime.now());
		
		theModel.addAttribute("studentCount", chartMap.studentCount());
		
		theModel.addAttribute("admissionCount", chartMap.admissionCount());
		
		theModel.addAttribute("newAdmissions", chartMap.newAdmission());
		
		theModel.addAttribute("higherIntegrated", chartMap.higherSecondaryIntegrated());
		
		theModel.addAttribute("higherNonIntegrated", chartMap.higherSecondaryNonIntegrated());
		
		theModel.addAttribute("newAdmissionStrength", chartMap.newAdmissionStrength());
		
		theModel.addAttribute("promotedGradeStrength", chartMap.promotedGradeStrength());

		return "dashboard";
	}


}
