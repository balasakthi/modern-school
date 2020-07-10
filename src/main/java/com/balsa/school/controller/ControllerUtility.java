package com.balsa.school.controller;

import com.balsa.school.bean.AdmissionStrength;
import com.balsa.school.bean.HigherSecondaryStrength;
import com.balsa.school.service.AcademicService;
import com.balsa.school.service.PaymentService;

public class ControllerUtility {

	private AcademicService academicService;
	private AdmissionStrength admissionStrength;
	private HigherSecondaryStrength higherSecondaryStrength;
	
	public ControllerUtility() {
		
	}

	public ControllerUtility(AcademicService academicService) {
		this.academicService = academicService;
	}

	

}
