package com.balsa.school.dao.specs;

import org.springframework.data.jpa.domain.Specification;

import com.balsa.school.entity.Academic;
import com.balsa.school.entity.Student;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AcademicSpecification implements Specification<Academic> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SearchCriteria> list;

	public AcademicSpecification() {
		this.list = new ArrayList<>();
	}

	public void add(SearchCriteria criteria) {
		list.add(criteria);
	}
	
	@Override
	public Predicate toPredicate(Root<Academic> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		// create a new predicate list
		List<Predicate> predicates = new ArrayList<>();
		
		Join<Academic,Student> joinQuery= query.from(Academic.class).join("student");
		
		joinQuery.on(builder.equal(joinQuery.get("student"),"d"));
		
		// add add criteria to predicates
		for (SearchCriteria criteria : list) {
			if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
				predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
				predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
				predicates
						.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
				predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
				predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
				predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase() + "%"));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						criteria.getValue().toString().toLowerCase() + "%"));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase()));
			} else if (criteria.getOperation().equals(SearchOperation.IN)) {
				predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
				predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
			}
		}

		return builder.and(predicates.toArray(new Predicate[0]));
	}
}