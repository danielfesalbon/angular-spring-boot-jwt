/**
 * 
 */
package com.rest.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rest.app.table.Transactions;
import com.rest.app.util.PaginateValues;
import com.rest.app.util.PurchaseProcess;

/**
 * @author danielf
 *
 */
public interface TransactionService {

	ResponseEntity<Map<String, Object>> submitTransaction(PurchaseProcess transaction);

	List<Transactions> getPurchaseTransactions(Integer row, Integer page, String status, String from, String to);

	ResponseEntity<PaginateValues> getPageValues(int row, String status, String from, String to);

	ResponseEntity<PurchaseProcess> getTransactionDetails(String transactionid);

	ResponseEntity<Map<String, Object>> updateTransaction(Transactions transaction);
	

}
