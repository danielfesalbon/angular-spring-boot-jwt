/**
 * 
 */
package com.rest.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.rest.app.table.Transactions;

/**
 * @author danielf
 *
 */
public interface TransactionsRepository extends JpaRepository<Transactions, String>,
		QueryByExampleExecutor<Transactions>, JpaSpecificationExecutor<Transactions> {

	List<Transactions> findAllByTransactiontype(String transactiontype, Pageable pageable);

	List<Transactions> findAllByTransactiontype(String transactiontype);

	Page<Transactions> findAll(Specification<Transactions> transactions, Pageable pageable);

}
