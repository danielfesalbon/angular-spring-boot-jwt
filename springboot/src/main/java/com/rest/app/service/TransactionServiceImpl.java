/**
 * 
 */
package com.rest.app.service;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class TransactionServiceImpl implements TransactionService {

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
			// transaction.getPurchasetx().setTransactionstatus("COMPLETED");
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
			res.put("event", "New Transaction " + transaction.getPurchasetx().getTransactionid() + " created.");
			res.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			res.put("flag", "failed");
			return ResponseEntity.badRequest().body(res);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(res);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<PurchaseProcess> getTransactionDetails(String transactionid) {
		// TODO Auto-generated method stub
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

	@Override
	public ResponseEntity<Map<String, Object>> updateTransaction(Transactions transaction) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			Transactions tx = TransactionRepository.findById(transaction.getTransactionid()).get();
			if (tx != null) {
				tx.setTransactionstatus(transaction.getTransactionstatus());
				TransactionRepository.save(tx);
				res.put("event", "Transaction " + tx.getTransactionid() + " updated.");
				res.put("flag", "success");
			} else {
				res.put("flag", "failed");
				return ResponseEntity.badRequest().body(res);
			}

		} catch (Exception e) {
			e.printStackTrace();
			res.put("flag", "failed");
			return ResponseEntity.badRequest().body(res);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(res);
	}

	@Override
	public List<Transactions> getPurchaseTransactions(Integer row, Integer page, String status, String from,
			String to) {
		try {
			Date start = new Date(), end = new Date();
			Pageable pagination = PageRequest.of(page, row, Sort.by(Sort.Direction.DESC, "transactionid"));
			Transactions tx = new Transactions();
			tx.setTransactiontype("SALE");
			if (!status.equals("undefined") && !status.equals("null") && status != null) {
				tx.setTransactionstatus(status);
			}
			if (from != null && !from.equals("null")) {
				start = new SimpleDateFormat("MM/dd/yyyy").parse(from);
			}
			if (to != null && !to.equals("null")) {
				end = new SimpleDateFormat("MM/dd/yyyy").parse(to);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(end);
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DATE);
				calendar.set(year, month, day, 23, 59, 59);
				end = calendar.getTime();
			}
			Example<Transactions> example = Example.of(tx);
			if (from != null && !from.equals("null") && to != null) {
				if ((!from.equals("null") && from.equals(to)) || to.equals("null")) {
					tx.setTransactiondate(start);
					example = Example.of(tx);
					return TransactionRepository.findAll(example, pagination).getContent();
				} else {
					return TransactionRepository
							.findAll(txutil.getSpecFromDatesAndExample(start, end, example), pagination).getContent();
				}
			} else {
				return TransactionRepository.findAll(example, pagination).getContent();
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new ArrayList<Transactions>();
	}

	@Override
	public ResponseEntity<PaginateValues> getPageValues(int row, String status, String from, String to) {
		try {
			Date start = new Date(), end = new Date();
			List<Integer> options = new ArrayList<Integer>();
			PaginateValues values = new PaginateValues();
			Transactions tx = new Transactions();
			tx.setTransactiontype("SALE");
			if (!status.equals("undefined") && !status.equals("null") && status != null) {
				tx.setTransactionstatus(status);
			}
			if (from != null && !from.equals("null")) {
				start = new SimpleDateFormat("MM/dd/yyyy").parse(from);
			}
			if (to != null && !to.equals("null")) {
				end = new SimpleDateFormat("MM/dd/yyyy").parse(to);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(end);
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DATE);
				calendar.set(year, month, day, 23, 59, 59);
				end = calendar.getTime();
			}
			Example<Transactions> example = Example.of(tx);
			if (from != null && !from.equals("null") && to != null) {
				if ((!from.equals("null") && from.equals(to)) || to.equals("null")) {
					tx.setTransactiondate(start);
					example = Example.of(tx);
					values.setCount(TransactionRepository.count(example));
				} else {
					values.setCount(
							TransactionRepository.count(txutil.getSpecFromDatesAndExample(start, end, example)));

				}
			} else {
				values.setCount(TransactionRepository.count(example));
			}
			// values.setCount(TransactionRepository.count(example));
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