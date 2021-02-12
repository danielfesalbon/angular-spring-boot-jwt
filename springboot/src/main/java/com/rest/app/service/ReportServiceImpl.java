/**
 * 
 */
package com.rest.app.service;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rest.app.util.ReportUtil;

/**
 * @author danielf
 *
 */
@Component
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportUtil reportUtil;

	@Override
	public ResponseEntity<Resource> generateReport(String status, String from, String to, String user) {
		// TODO Auto-generated method stub
		try {
			File file = reportUtil.generateReport(status, from, to, user);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0");
			return ResponseEntity.ok().headers(header).contentLength(file.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

}
