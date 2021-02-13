/**
 * 
 */
package com.rest.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.app.service.ReportService;

/**
 * @author danielf
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/file")
@Component
public class ReportController {

	@Autowired
	private ReportService reportService;

	@GetMapping("/generate")
	public ResponseEntity<Resource> generateReport(@RequestParam(required = false, name = "status") String status,
			@RequestParam(required = false, name = "from") String from,
			@RequestParam(required = false, name = "to") String to,
			@RequestParam(required = false, name = "user") String user) {
		return reportService.generateReport(status, from, to, user);
	}
	
	@GetMapping("/receipt")
	public ResponseEntity<Resource> generateReceipt(@RequestParam(required = false, name = "txid") String txid) {
		return reportService.generateReceipt(txid);
	}
	
	

}
