/**
 * 
 */
package com.rest.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.app.service.TransactionService;
import com.rest.app.table.Transactions;
import com.rest.app.util.PaginateValues;
import com.rest.app.util.PurchaseProcess;

/**
 * @author danielf
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/transaction")
@Component
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/submit")
	public ResponseEntity<Map<String, Object>> submitTransaction(@RequestBody PurchaseProcess transaction) {
		return transactionService.submitTransaction(transaction);
	}

	@GetMapping("/list")
	public List<Transactions> getPurchaseTransactions(@RequestParam(required = false, name = "row") Integer row,
			@RequestParam(required = false, name = "page") Integer page,
			@RequestParam(required = false, name = "status") String status,
			@RequestParam(required = false, name = "from") String from,
			@RequestParam(required = false, name = "to") String to) {
		return transactionService.getPurchaseTransactions(row, page, status, from, to);
	}

	@GetMapping("/page")
	public ResponseEntity<PaginateValues> getPageValues(@RequestParam(required = false, name = "row") Integer row,
			@RequestParam(required = false, name = "status") String status,
			@RequestParam(required = false, name = "from") String from,
			@RequestParam(required = false, name = "to") String to) {
		return transactionService.getPageValues(row, status, from, to);
	}

	@GetMapping("/get/{transactionid}")
	public ResponseEntity<PurchaseProcess> getTransactionDetails(@PathVariable String transactionid) {
		return transactionService.getTransactionDetails(transactionid);
	}

	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updateTransaction(@RequestBody Transactions transaction) {
		return transactionService.updateTransaction(transaction);
	}

}
