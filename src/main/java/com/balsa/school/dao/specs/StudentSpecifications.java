package com.balsa.school.dao.specs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.balsa.school.entity.Student;

public class StudentSpecifications {

	public static Specification<Student> getByFullName(String fullName) {

		return new Specification<Student>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				Path fullNameExpression = root.get("fullName");

				predicates.add(criteriaBuilder.like(fullNameExpression, "%"+fullName+"%"));

				if (predicates.isEmpty()) {
					return criteriaBuilder.conjunction();
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}

		};
	}

}
