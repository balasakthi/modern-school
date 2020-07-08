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
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotEmpty
	@Column(name = "full_name")
	private String fullName;

	@JsonIgnore
	@OneToMany(mappedBy = "student", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<Academic> academic;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<Academic> getAcademic() {
		return academic;
	}

	public void setAcademic(List<Academic> academic) {
		this.academic = academic;
	}

	// Bi Directional relationship
	public void add(Academic tempStandard) {
		if (academic == null) {
			academic = new ArrayList<>();
		}
		academic.add(tempStandard);
		tempStandard.setStudent(this);
	}

}
