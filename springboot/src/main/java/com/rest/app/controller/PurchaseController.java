///**
// * 
// */
//package com.rest.app.controller;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.rest.app.service.PurchaseService;
//import com.rest.app.table.Transactions;
//import com.rest.app.util.PaginateValues;
//import com.rest.app.util.PurchaseProcess;
//
///**
// * @author danielf
// *
// */
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/purchase")
//@Component
//public class PurchaseController {
//
//	@Autowired
//	private PurchaseService purchaseService;
//
//	@PostMapping("/submit")
//	public ResponseEntity<Map<String, Object>> processPurchase(@RequestBody PurchaseProcess purchase) {
//		return purchaseService.processPurchase(purchase);
//	}
//
//	@GetMapping("/list")
//	public List<Transactions> getPurchaseTransactions(@RequestParam(required = false, name = "row") Integer row,
//			@RequestParam(required = false, name = "page") Integer page) {
//		return purchaseService.getPurchaseTransactions(row, page);
//	}
//
//	@GetMapping("/get/{transactionid}")
//	public ResponseEntity<PurchaseProcess> getPurchaseDetails(@PathVariable String transactionid) {
//		return purchaseService.getPurchaseDetails(transactionid);
//	}
//
//	@GetMapping("/page/{row}")
//	public ResponseEntity<PaginateValues> getPageValues(@PathVariable int row) {
//		return purchaseService.getPageValues(row);
//	}
//
//}
