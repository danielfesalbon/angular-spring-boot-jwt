/**
 * 
 */
package com.rest.app.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.rest.app.table.Transactions;

/**
 * @author danielf
 *
 */
@Component
public class TransactionUtil {

	public String GenerateID(long count) {
		try {

			Date today = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);

			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR) - 2000;
			year = year * 100;
			year = year + month;
			year = year * 10000;
			count = count + 1;
			year = year + (int) count;
			String id = String.valueOf(year);
			return id;

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	public Specification<Transactions> getSpecFromDatesAndExample(Date from, Date to, Example<Transactions> example) {
		return (Specification<Transactions>) (root, query, builder) -> {
			final List<Predicate> predicates = new ArrayList<>();

			if (from != null) {
				predicates.add(builder.greaterThan(root.get("transactiondate"), from));
			}
			if (to != null) {
				predicates.add(builder.lessThan(root.get("transactiondate"), to));
			}
			predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));

			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}

}
