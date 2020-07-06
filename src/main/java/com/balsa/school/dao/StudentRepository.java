package com.balsa.school.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.balsa.school.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
