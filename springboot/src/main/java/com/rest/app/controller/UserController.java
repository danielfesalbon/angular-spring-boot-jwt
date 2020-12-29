/**
 * 
 */
package com.rest.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.app.service.UseraccountRepository;
import com.rest.app.table.Useraccount;

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
	private UseraccountRepository userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/list")
	public List<Useraccount> getUsers() {

		List<Useraccount> list = new ArrayList<Useraccount>();
		try {

			return userService.findAll();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> saveUser(@RequestBody Useraccount user) {

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			response.put("event", "Updated " + user.getName() + "'s user account details");
			if (user.getUserid() == null || user.getUserid() == 0) {
				response.put("event", "Saved new user account");
				user.setDisabled(true);
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			userService.save(user);
			response.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/delete/{userid}")
	public ResponseEntity<Map<String, Object>> deteleUser(@PathVariable Long userid) {

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			Useraccount user = userService.findById(userid).get();
			if (user != null) {
				userService.delete(user);
				response.put("event", "Deleted " + user.getName() + "'s user account");
				response.put("flag", "success");
			} else {
				response.put("flag", "failed");
				return new ResponseEntity(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

}
