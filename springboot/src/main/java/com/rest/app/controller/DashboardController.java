/**
 * 
 */
package com.rest.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.app.util.SettingsProperties;

/**
 * @author danielf
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/dashboard")
@Component
public class DashboardController {

	@Autowired
	private SettingsProperties properties;

	@GetMapping("/settings")
	public ResponseEntity<SettingsProperties> getProperties() {
		return ResponseEntity.ok(new SettingsProperties(properties.getDb_url(), properties.getDb_user(),
				properties.getDb_password(), properties.getApp_port()));
	}

}
