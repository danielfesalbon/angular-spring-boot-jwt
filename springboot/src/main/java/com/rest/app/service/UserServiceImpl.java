/**
 * 
 */
package com.rest.app.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.rest.app.repo.UseraccountRepository;
import com.rest.app.table.Useraccount;

/**
 * @author danielf
 *
 */
@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UseraccountRepository UserRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<Useraccount> getUsers() {

		List<Useraccount> list = new ArrayList<Useraccount>();
		try {

			return UserRepository.findAll();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}

	public ResponseEntity<Map<String, Object>> saveUser(Useraccount user) {

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			response.put("event", "Updated " + user.getName() + "'s user account details");
			if (user.getUserid() == null || user.getUserid() == 0) {
				response.put("event", "Saved new user account");
				user.setDisabled(true);
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			UserRepository.save(user);
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
	public ResponseEntity<Map<String, Object>> deteleUser(Long userid) {

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			Useraccount user = UserRepository.findById(userid).get();
			if (user != null) {
				UserRepository.delete(user);
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

	@Override
	public ResponseEntity<Map<String, Object>> validateReset(Useraccount user) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
			response.put("flag", "failed");
			response.put("event", "User Account Recovery validation failed");
			if (user.getUsername() != null && user.getBday() != null) {
				Useraccount u = UserRepository.findByUsername(user.getUsername());
				if (dateformatter.format(u.getBday()).equals(dateformatter.format(user.getBday()))) {
					response.put("flag", "success");
					response.put("event", "User Account Recovery validated");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> resetPassword(Useraccount user) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("flag", "failed");
			response.put("event", "User Account Reset");
			Useraccount u = UserRepository.findByUsername(user.getUsername());
			u.setPassword(passwordEncoder.encode(user.getPassword()));
			UserRepository.save(u);
			response.put("flag", "success");
			// event.LOG_EVENT(u.getUsername(), response);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

}