/**
 * 
 */
package com.rest.app.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author danielf
 *
 */
@RestController
@RequestMapping("/hello")
@Component
public class TestController {

	@GetMapping("/world")
	public String testMethod() {
		return "Hello World!";
	}

}
