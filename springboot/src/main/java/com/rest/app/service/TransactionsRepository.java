/**
 * 
 */
package com.rest.app.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Transactions;

/**
 * @author danielf
 *
 */
public interface TransactionsRepository extends JpaRepository<Transactions, String> {

}
