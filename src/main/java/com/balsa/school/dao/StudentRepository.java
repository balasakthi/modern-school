package com.balsa.school.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.balsa.school.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>, 
JpaSpecificationExecutor<Student> {



}
