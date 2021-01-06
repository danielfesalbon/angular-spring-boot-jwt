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
import org.springframework.http.HttpStatus;
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
public class PurchaseService {
	@Autowired
	private PurchaseRepository PurchaseRepository;

	@Autowired
	private TransactionUtil txutil = new TransactionUtil();

	@Autowired
	private TransactionsRepository TransactionRepository;

	@Autowired
	private ProductRepository ProductRepository;

	public ResponseEntity<Map<String, Object>> processPurchase(PurchaseProcess purchase) {

		Map<String, Object> res = new HashMap<String, Object>();

		try {
			// status -> COMPLETED, VOID
			// type -> SUPPLY, SALE
			purchase.getPurchasetx().setTransactionid(txutil.GenerateID(TransactionRepository.count()));
			purchase.getPurchasetx().setTransactiondate(new Date());
			purchase.getPurchasetx().setTransactiontime(new Time(new Date().getTime()));
			purchase.getPurchasetx().setTransactionstatus("COMPLETED");
			purchase.getPurchasetx().setTransactiontype("SUPPLY");
			for (Productpertransaction p : purchase.getProdpertrans()) {
				Product prod = ProductRepository.findById(p.getProductid()).get();
				if (prod != null) {
					prod.setStock(prod.getStock() + p.getQuantity());
					prod.setLastmax(prod.getStock());
					p.setTransactionid(purchase.getPurchasetx().getTransactionid());
					ProductRepository.save(prod);
					PurchaseRepository.save(p);
				}
			}
			TransactionRepository.save(purchase.getPurchasetx());
			res.put("event", "New Supply " + purchase.getPurchasetx().getTransactionid());
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
			tx.setTransactiontype("SUPPLY");
			Example<Transactions> example = Example.of(tx);
			return TransactionRepository.findAll(example, pagination).getContent();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new ArrayList<Transactions>();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })

	public ResponseEntity<PurchaseProcess> getPurchaseDetails(String transactionid) {

		PurchaseProcess purchase = new PurchaseProcess();

		try {

			Productpertransaction prod = new Productpertransaction();
			prod.setTransactionid(transactionid);
			Example<Productpertransaction> example = Example.of(prod);
			purchase.setPurchasetx(TransactionRepository.findById(transactionid).get());
			purchase.setProdpertrans(PurchaseRepository.findAll(example));

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Invalid routine", HttpStatus.BAD_REQUEST);
			// TODO: handle exception
		}
		return ResponseEntity.ok(purchase);
	}

	public ResponseEntity<PaginateValues> getPageValues(int row) {
		try {
			List<Integer> options = new ArrayList<Integer>();
			PaginateValues values = new PaginateValues();
			Transactions tx = new Transactions();
			tx.setTransactiontype("SUPPLY");
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
