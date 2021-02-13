/**
 * 
 */
package com.rest.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.app.config.SecurityService;
import com.rest.app.util.LoginRequest;

/**
 * @author danielf
 * @see https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

	@Autowired
	private SecurityService securityService;

	@PostMapping("/user")
	public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody LoginRequest requestcredentials) {
		return securityService.authenticateUser(requestcredentials);
	}

}
