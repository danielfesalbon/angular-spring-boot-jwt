/**
 * 
 */
package com.rest.app.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

/**
 * @author danielf
 *
 */
public interface ReportService {
	
	ResponseEntity<Resource> generateReport(String status, String from, String to, String user);

}
