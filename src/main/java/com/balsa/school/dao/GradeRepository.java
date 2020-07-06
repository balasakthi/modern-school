package com.balsa.school.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.balsa.school.entity.Grade;

public interface GradeRepository extends JpaRepository<Grade, Integer> {

}
