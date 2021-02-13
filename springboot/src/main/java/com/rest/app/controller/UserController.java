/**
 * 
 */
package com.rest.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.app.service.UserService;
import com.rest.app.table.Audittrail;
import com.rest.app.table.Useraccount;
import com.rest.app.util.PaginateValues;

/**
 * @author danielf
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
@Component
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/list")
	public List<Useraccount> getUsers() {
		return userService.getUsers();
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> saveUser(@RequestBody Useraccount user) {
		return userService.saveUser(user);
	}

	@DeleteMapping("/delete/{userid}")
	public ResponseEntity<Map<String, Object>> deteleUser(@PathVariable Long userid) {
		return userService.deteleUser(userid);
	}

	@PostMapping("/validate/reset")
	public ResponseEntity<Map<String, Object>> validateReset(@RequestBody Useraccount user) {
		return userService.validateReset(user);
	}

	@PostMapping("/reset/password")
	public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody Useraccount user) {
		return userService.resetPassword(user);
	}

	@GetMapping("/activity")
	public List<Audittrail> getActivity(@RequestParam(required = false, name = "row") Integer row,
			@RequestParam(required = false, name = "page") Integer page) {
		return userService.getActivity(row, page);
	}

	@GetMapping("/activity/page")
	public ResponseEntity<PaginateValues> getPageValues(@RequestParam(required = false, name = "row") Integer row) {
		return userService.getPageValues(row);
	}
	
	@PostMapping("/logout/{user}")
	public ResponseEntity<Map<String, Object>> signOut(@PathVariable String user) {
		return userService.signOut(user);
	}

}
