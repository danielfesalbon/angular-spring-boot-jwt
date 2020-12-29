/**
 * 
 */
package com.rest.app.controller;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.app.service.ProductRepository;
import com.rest.app.service.PurchaseRepository;
import com.rest.app.service.TransactionsRepository;
import com.rest.app.table.Product;
import com.rest.app.table.Productpertransaction;
import com.rest.app.table.Transactions;
import com.rest.app.util.PurchaseProcess;
import com.rest.app.util.TransactionUtil;

/**
 * @author danielf
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/purchase")
@Component
public class PurchaseController {

	@Autowired
	private PurchaseRepository purchaseService;

	@Autowired
	private TransactionUtil txutil = new TransactionUtil();

	@Autowired
	private TransactionsRepository transactionService;

	@Autowired
	private ProductRepository productService;

	@PostMapping("/submit")
	public ResponseEntity<Map<String, Object>> processPurchase(@RequestBody PurchaseProcess purchase) {

		Map<String, Object> res = new HashMap<String, Object>();

		try {
			// status -> COMPLETED, VOID
			// type -> SUPPLY, SALE
			purchase.getPurchasetx().setTransactionid(txutil.GenerateID(transactionService.count()));
			purchase.getPurchasetx().setTransactiondate(new Date());
			purchase.getPurchasetx().setTransactiontime(new Time(new Date().getTime()));
			purchase.getPurchasetx().setTransactionstatus("COMPLETED");
			purchase.getPurchasetx().setTransactiontype("SUPPLY");
			for (Productpertransaction p : purchase.getProdpertrans()) {
				Product prod = productService.findById(p.getProductid()).get();
				if (prod != null) {
					prod.setStock(prod.getStock() + p.getQuantity());
					prod.setLastmax(prod.getStock());
					p.setTransactionid(purchase.getPurchasetx().getTransactionid());
					productService.save(prod);
					purchaseService.save(p);
				}
			}
			transactionService.save(purchase.getPurchasetx());
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

	@GetMapping("/list")
	public List<Transactions> getPurchaseTransactions() {
		try {
			Transactions tx = new Transactions();
			tx.setTransactiontype("SUPPLY");
			Example<Transactions> example = Example.of(tx);
			return transactionService.findAll(example);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/get/{transactionid}")
	public ResponseEntity<PurchaseProcess> getPurchaseDetails(@PathVariable String transactionid) {

		PurchaseProcess purchase = new PurchaseProcess();

		try {

			Productpertransaction prod = new Productpertransaction();
			prod.setTransactionid(transactionid);
			Example<Productpertransaction> example = Example.of(prod);
			purchase.setPurchasetx(transactionService.findById(transactionid).get());
			purchase.setProdpertrans(purchaseService.findAll(example));

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Invalid routine", HttpStatus.BAD_REQUEST);
			// TODO: handle exception
		}
		return ResponseEntity.ok(purchase);
	}

}
