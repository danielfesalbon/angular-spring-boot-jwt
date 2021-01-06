/**
 * 
 */
package com.rest.app.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rest.app.repo.ProductRepository;
import com.rest.app.repo.PurchaseRepository;
import com.rest.app.repo.TransactionsRepository;
import com.rest.app.table.Product;
import com.rest.app.table.Productpertransaction;
import com.rest.app.table.Transactions;
import com.rest.app.util.PaginateValues;
import com.rest.app.util.PurchaseProcess;
import com.rest.app.util.TransactionUtil;

/**
 * @author danielf
 *
 */
@Component
public class TransactionService {

	@Autowired
	private PurchaseRepository PurchaseRepository;

	@Autowired
	private TransactionUtil txutil = new TransactionUtil();

	@Autowired
	private TransactionsRepository TransactionRepository;

	@Autowired
	private ProductRepository ProductRepository;

	public ResponseEntity<Map<String, Object>> submitTransaction(PurchaseProcess transaction) {

		Map<String, Object> res = new HashMap<String, Object>();

		try {
			// status -> COMPLETED, VOID
			// type -> SUPPLY, SALE
			transaction.getPurchasetx().setTransactionid(txutil.GenerateID(TransactionRepository.count()));
			transaction.getPurchasetx().setTransactiondate(new Date());
			transaction.getPurchasetx().setTransactiontime(new Time(new Date().getTime()));
			transaction.getPurchasetx().setTransactionstatus("COMPLETED");
			transaction.getPurchasetx().setTransactiontype("SALE");
			for (Productpertransaction p : transaction.getProdpertrans()) {
				Product prod = ProductRepository.findById(p.getProductid()).get();
				if (prod != null) {
					prod.setStock(prod.getStock() - p.getQuantity());
					p.setTransactionid(transaction.getPurchasetx().getTransactionid());
					ProductRepository.save(prod);
					PurchaseRepository.save(p);
				}
			}
			TransactionRepository.save(transaction.getPurchasetx());
			res.put("event", "Transaction " + transaction.getPurchasetx().getTransactionid() + " completed.");
			res.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			res.put("flag", "failed");
			return ResponseEntity.badRequest().body(res);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(res);
	}

	public List<Transactions> getPurchaseTransactions(Integer row, Integer page) {
		try {
			Pageable pagination = PageRequest.of(page, row, Sort.by("transactionid"));
			Transactions tx = new Transactions();
			tx.setTransactiontype("SALE");
			Example<Transactions> example = Example.of(tx);
			return TransactionRepository.findAll(example, pagination).getContent();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new ArrayList<Transactions>();
	}

	public ResponseEntity<PaginateValues> getPageValues(int row) {
		try {
			List<Integer> options = new ArrayList<Integer>();
			PaginateValues values = new PaginateValues();
			Transactions tx = new Transactions();
			tx.setTransactiontype("SALE");
			Example<Transactions> example = Example.of(tx);
			values.setCount(TransactionRepository.count(example));
			values.setRow(row);
			if (values.getCount() >= 20) {
				options = new ArrayList<Integer>();
				options.add(10);
				options.add(15);
				options.add(20);
			} else if (values.getCount() >= 15 && values.getCount() < 20) {
				options = new ArrayList<Integer>();
				options.add(10);
				options.add(15);
			} else if (values.getCount() >= 10 && values.getCount() < 15) {
				options = new ArrayList<Integer>();
				options.add(10);
			} else {
				options = new ArrayList<Integer>();
				options.add((int) values.getCount());
			}
			values.setRowoptions(options);
			return ResponseEntity.ok(values);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return ResponseEntity.ok(new PaginateValues());
	}

}
