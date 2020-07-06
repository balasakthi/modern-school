package com.balsa.school.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "grade")
public class Grade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "annual_fee")
	private int annualFee;

	@JsonIgnore
	@OneToMany(mappedBy = "grade", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<Academic> academic;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(int annualFee) {
		this.annualFee = annualFee;
	}

	public List<Academic> getAcademic() {
		return academic;
	}

	public void setAcademic(List<Academic> academic) {
		this.academic = academic;
	}

	// Bi Directional relationship
	public void add(Academic tempAcademic) {
		if (academic == null) {
			academic = new ArrayList<>();
		}
		academic.add(tempAcademic);
		tempAcademic.setGrade(this);
	}

}
